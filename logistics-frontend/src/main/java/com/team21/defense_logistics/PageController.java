package com.team21.defense_logistics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;

@Controller
public class PageController {

    private final MockStore store;

    public PageController(MockStore store) { this.store = store; }

    @ModelAttribute("user")
    public String user(HttpSession session) {
        Object u = session.getAttribute("user");
        return u == null ? "" : u.toString();
    }

    // ---------- dashboard ----------

    @GetMapping("/")
    public String dashboard(Model model) {
        List<Map<String, Object>> meters = new ArrayList<>();
        long readySum = 0, totalSum = 0;
        String[][] groups = {{"personnel", "Personnel", "bi-people"},
                             {"vehicles", "Vehicles", "bi-truck"},
                             {"equipment", "Equipment", "bi-hdd-rack"}};
        for (String[] g : groups) {
            long ready = store.count(g[0], "AVAILABLE");
            long total = store.total(g[0]);
            readySum += ready;
            totalSum += total;
            long pct = total == 0 ? 0 : ready * 100 / total;
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("label", g[1]);
            row.put("icon", g[2]);
            row.put("ready", ready);
            row.put("total", total);
            row.put("pct", pct);
            row.put("level", pct >= 75 ? "ok" : pct >= 50 ? "warn" : "bad");
            meters.add(row);
        }
        long overall = totalSum == 0 ? 0 : readySum * 100 / totalSum;

        model.addAttribute("active", "dashboard");
        model.addAttribute("meters", meters);
        model.addAttribute("overall", overall);
        model.addAttribute("overallLabel", overall >= 75 ? "READY" : overall >= 50 ? "PARTIAL" : "CRITICAL");
        model.addAttribute("pending", store.count("requests", "PENDING"));
        model.addAttribute("allocatedNow", store.count("allocations", "ACTIVE"));
        model.addAttribute("maintenanceOpen",
                store.count("maintenance", "SCHEDULED") + store.count("maintenance", "IN_PROGRESS"));
        model.addAttribute("lowStock", store.count("supplies", "LOW"));

        List<Map<String, String>> reqs = new ArrayList<>(store.get("requests").rows());
        Collections.reverse(reqs);
        model.addAttribute("recent", reqs.size() > 5 ? reqs.subList(0, 5) : reqs);
        model.addAttribute("lowItems", store.get("supplies").rows().stream()
                .filter(r -> "LOW".equals(r.get("status"))).toList());

        List<String> statuses = store.get("requests").options().get("status");
        model.addAttribute("chartLabels", statuses);
        model.addAttribute("chartData", statuses.stream().map(s -> store.count("requests", s)).toList());
        return "dashboard";
    }

    // ---------- list / detail / form ----------

    private void fill(Model model, MockStore.Module m) {
        Map<String, String> labels = new LinkedHashMap<>();
        for (String c : m.columns()) labels.put(c, MockStore.label(c));
        for (String c : m.formCols()) labels.put(c, MockStore.label(c));
        model.addAttribute("key", m.key());
        model.addAttribute("active", m.key());
        model.addAttribute("title", m.title());
        model.addAttribute("subtitle", m.subtitle());
        model.addAttribute("icon", m.icon());
        model.addAttribute("columns", m.columns());
        model.addAttribute("labels", labels);
        model.addAttribute("canAdd", m.canAdd());
    }

    @GetMapping("/m/{key}")
    public String list(@PathVariable String key,
                       @RequestParam(required = false) String status, Model model) {
        var m = store.get(key);
        if (m == null) return "redirect:/";
        List<Map<String, String>> rows = m.rows().stream()
                .filter(r -> status == null || status.isBlank() || status.equals(r.get("status")))
                .toList();
        fill(model, m);
        model.addAttribute("rows", rows);
        model.addAttribute("total", m.rows().size());
        model.addAttribute("activeStatus", status == null ? "" : status);
        model.addAttribute("chips", m.options().getOrDefault("status", List.of()));
        return "list";
    }

    @GetMapping("/m/{key}/view/{id}")
    public String view(@PathVariable String key, @PathVariable String id, Model model) {
        var m = store.get(key);
        var row = store.find(key, id);
        if (m == null || row == null) return "redirect:/m/" + key;
        fill(model, m);
        model.addAttribute("row", row);
        model.addAttribute("related", key.equals("requests")
                ? store.get("allocations").rows().stream().filter(a -> id.equals(a.get("requestId"))).toList()
                : List.of());
        return "detail";
    }

    @GetMapping("/m/{key}/new")
    public String newForm(@PathVariable String key, Model model) {
        var m = store.get(key);
        if (m == null || !m.canAdd()) return "redirect:/";
        return form(m, new LinkedHashMap<>(), model, "Add new");
    }

    @GetMapping("/m/{key}/edit/{id}")
    public String editForm(@PathVariable String key, @PathVariable String id, Model model) {
        var m = store.get(key);
        var row = store.find(key, id);
        if (m == null || row == null || !m.canAdd()) return "redirect:/";
        return form(m, row, model, "Edit");
    }

    private String form(MockStore.Module m, Map<String, String> row, Model model, String mode) {
        fill(model, m);
        Map<String, List<String>> opts = new LinkedHashMap<>();
        Map<String, String> types = new LinkedHashMap<>();
        for (String c : m.formCols()) {
            List<String> o = store.optionsFor(m.key(), c);
            if (!o.isEmpty()) opts.put(c, o);
            if (c.equals("dueDate")) types.put(c, "date");
            if (c.equals("quantity") || c.equals("minLevel") || c.equals("strength")) types.put(c, "number");
        }
        model.addAttribute("formCols", m.formCols());
        model.addAttribute("opts", opts);
        model.addAttribute("types", types);
        model.addAttribute("row", row);
        model.addAttribute("mode", mode);
        return "form";
    }

    // ---------- actions ----------

    @PostMapping("/m/{key}/save")
    public String save(@PathVariable String key, @RequestParam Map<String, String> form,
                       RedirectAttributes ra) {
        var m = store.get(key);
        if (m == null || !m.canAdd()) return "redirect:/";
        store.save(key, form);
        ra.addFlashAttribute("msg", "Record saved successfully");
        ra.addFlashAttribute("ok", true);
        return "redirect:/m/" + key;
    }

    @GetMapping("/m/{key}/delete/{id}")
    public String delete(@PathVariable String key, @PathVariable String id, RedirectAttributes ra) {
        var m = store.get(key);
        if (m != null && m.canAdd()) {
            store.delete(key, id);
            ra.addFlashAttribute("msg", "Record deleted");
            ra.addFlashAttribute("ok", true);
        }
        return "redirect:/m/" + key;
    }

    @GetMapping("/m/{key}/status/{id}/{value}")
    public String status(@PathVariable String key, @PathVariable String id,
                         @PathVariable String value, RedirectAttributes ra) {
        store.setStatus(key, id, value);
        ra.addFlashAttribute("msg", "Status updated to " + value.replace('_', ' '));
        ra.addFlashAttribute("ok", true);
        return "redirect:/m/" + key;
    }

    @GetMapping("/requests/{id}/{action}")
    public String workflow(@PathVariable String id, @PathVariable String action,
                           @RequestParam(required = false) String back, RedirectAttributes ra) {
        MockStore.Result res = switch (action) {
            case "approve" -> store.approve(id);
            case "reject" -> store.reject(id);
            case "allocate" -> store.allocate(id);
            case "complete" -> store.complete(id);
            default -> new MockStore.Result(false, "Unknown action");
        };
        ra.addFlashAttribute("msg", res.message());
        ra.addFlashAttribute("ok", res.ok());
        return "redirect:" + ("detail".equals(back) ? "/m/requests/view/" + id : "/m/requests");
    }
}