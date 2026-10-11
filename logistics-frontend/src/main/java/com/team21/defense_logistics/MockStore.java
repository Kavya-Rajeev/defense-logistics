package com.team21.defense_logistics;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class MockStore {

    public record Module(String key, String title, String subtitle, String icon,
                         List<String> columns, List<String> formCols,
                         Map<String, List<String>> options, boolean canAdd,
                         String defaultStatus, List<Map<String, String>> rows) {}

    public record Result(boolean ok, String message) {}

    private final Map<String, Module> modules = new LinkedHashMap<>();
    private int nextId = 1;

    public MockStore() {
        define("personnel", "Personnel", "Roles, units, postings and availability", "bi-people",
                List.of("name", "rank", "unit", "posting", "status"),
                List.of("name", "rank", "unit", "posting", "status"),
                Map.of("status", List.of("AVAILABLE", "DEPLOYED", "ON_LEAVE"),
                       "rank", List.of("Colonel", "Major", "Captain", "Lieutenant", "Sergeant", "Corporal")),
                true, "AVAILABLE");
        define("units", "Units", "Operational units and their locations", "bi-diagram-3",
                List.of("name", "type", "location", "commander", "strength"),
                List.of("name", "type", "location", "commander", "strength"),
                Map.of("type", List.of("INFANTRY", "LOGISTICS", "SIGNALS", "MEDICAL", "ENGINEERS")),
                true, null);
        define("equipment", "Equipment & Assets", "Asset records, location, ownership and condition", "bi-hdd-rack",
                List.of("name", "type", "location", "ownership", "condition", "status"),
                List.of("name", "type", "location", "ownership", "condition", "status"),
                Map.of("type", List.of("GENERATOR", "RADIO", "MEDICAL", "TENT", "TOOLKIT"),
                       "condition", List.of("GOOD", "FAIR", "POOR"),
                       "status", List.of("AVAILABLE", "ASSIGNED", "UNDER_MAINTENANCE")),
                true, "AVAILABLE");
        define("vehicles", "Vehicles & Transport", "Fleet, drivers and availability", "bi-truck",
                List.of("name", "type", "driver", "status"),
                List.of("name", "type", "driver", "status"),
                Map.of("type", List.of("TRUCK", "JEEP", "AMBULANCE", "BUS"),
                       "status", List.of("AVAILABLE", "ASSIGNED", "UNDER_MAINTENANCE")),
                true, "AVAILABLE");
        define("supplies", "Supplies & Inventory", "Stock levels and replenishment alerts", "bi-box-seam",
                List.of("item", "quantity", "minLevel", "status"),
                List.of("item", "quantity", "minLevel"),
                Map.of("status", List.of("OK", "LOW")),
                true, "OK");
        define("requests", "Logistics Requests", "Create, approve, allocate and track requests", "bi-clipboard-check",
                List.of("unit", "vehicleType", "equipmentType", "priority", "status"),
                List.of("unit", "vehicleType", "equipmentType", "priority"),
                Map.of("vehicleType", List.of("TRUCK", "JEEP", "AMBULANCE", "BUS"),
                       "equipmentType", List.of("GENERATOR", "RADIO", "MEDICAL", "TENT", "TOOLKIT"),
                       "priority", List.of("LOW", "MEDIUM", "HIGH"),
                       "status", List.of("PENDING", "APPROVED", "ALLOCATED", "COMPLETED", "REJECTED")),
                true, "PENDING");
        define("allocations", "Allocations", "Resources assigned to requests", "bi-link-45deg",
                List.of("request", "vehicle", "equipment", "date", "status"),
                List.of(),
                Map.of("status", List.of("ACTIVE", "RELEASED")),
                false, "ACTIVE");
        define("maintenance", "Maintenance", "Preventive and corrective jobs with service history", "bi-wrench-adjustable",
                List.of("asset", "type", "task", "dueDate", "status"),
                List.of("asset", "type", "task", "dueDate", "status"),
                Map.of("type", List.of("PREVENTIVE", "CORRECTIVE"),
                       "status", List.of("SCHEDULED", "IN_PROGRESS", "COMPLETED")),
                true, "SCHEDULED");

        seed("units", "1st Logistics Battalion", "LOGISTICS", "Pune", "Col. Mehta", "420");
        seed("units", "Alpha Company", "INFANTRY", "Delhi", "Maj. Sharma", "120");
        seed("units", "Signals Detachment", "SIGNALS", "Jaipur", "Capt. Iyer", "60");

        seed("personnel", "Rajesh Kumar", "Captain", "Alpha Company", "Delhi", "AVAILABLE");
        seed("personnel", "Anita Nair", "Major", "1st Logistics Battalion", "Pune", "AVAILABLE");
        seed("personnel", "Vikram Singh", "Lieutenant", "Alpha Company", "Delhi", "DEPLOYED");
        seed("personnel", "Suresh Rao", "Sergeant", "Signals Detachment", "Jaipur", "AVAILABLE");

        seed("vehicles", "Truck-01", "TRUCK", "Sgt. Singh", "AVAILABLE");
        seed("vehicles", "Truck-02", "TRUCK", "Cpl. Das", "AVAILABLE");
        seed("vehicles", "Jeep-01", "JEEP", "Sgt. Rao", "UNDER_MAINTENANCE");
        seed("vehicles", "Ambulance-01", "AMBULANCE", "Cpl. Nair", "AVAILABLE");

        seed("equipment", "Generator G1", "GENERATOR", "Pune", "Army", "GOOD", "AVAILABLE");
        seed("equipment", "Generator G2", "GENERATOR", "Delhi", "Army", "GOOD", "AVAILABLE");
        seed("equipment", "Radio R7", "RADIO", "Jaipur", "Army", "FAIR", "AVAILABLE");
        seed("equipment", "Medical Kit M1", "MEDICAL", "Pune", "Army", "GOOD", "AVAILABLE");

        seed("supplies", "Fuel (litres)", "800", "500");
        seed("supplies", "Rations (packs)", "120", "50");
        seed("supplies", "Medical Kits", "40", "60");

        seed("requests", "Alpha Company", "TRUCK", "GENERATOR", "HIGH", "PENDING");
        seed("requests", "1st Logistics Battalion", "AMBULANCE", "MEDICAL", "MEDIUM", "PENDING");
        seed("requests", "Signals Detachment", "JEEP", "RADIO", "LOW", "PENDING");

        seed("maintenance", "Jeep-01", "CORRECTIVE", "Engine overhaul", "2026-10-05", "IN_PROGRESS");
        seed("maintenance", "Truck-01", "PREVENTIVE", "Oil and filter change", "2026-10-25", "SCHEDULED");
        seed("maintenance", "Truck-02", "PREVENTIVE", "Brake inspection", "2026-09-20", "COMPLETED");
    }

    // ---------- setup helpers ----------

    private void define(String key, String title, String subtitle, String icon,
                        List<String> columns, List<String> formCols,
                        Map<String, List<String>> options, boolean canAdd, String defaultStatus) {
        modules.put(key, new Module(key, title, subtitle, icon, columns, formCols,
                options, canAdd, defaultStatus, new ArrayList<>()));
    }

    private void seed(String key, String... values) {
        Module m = modules.get(key);
        Map<String, String> row = new LinkedHashMap<>();
        row.put("id", String.valueOf(nextId++));
        for (int i = 0; i < values.length; i++) row.put(m.columns().get(i), values[i]);
        recalc(key, row);
        m.rows().add(row);
    }

    public static String label(String c) {
        StringBuilder sb = new StringBuilder();
        for (char ch : c.toCharArray()) {
            if (Character.isUpperCase(ch)) sb.append(' ');
            sb.append(sb.length() == 0 ? Character.toUpperCase(ch) : ch);
        }
        return sb.toString();
    }

    // ---------- reading ----------

    public Module get(String key) { return modules.get(key); }

    public Map<String, String> find(String key, String id) {
        Module m = modules.get(key);
        if (m == null || id == null) return null;
        return m.rows().stream().filter(r -> id.equals(r.get("id"))).findFirst().orElse(null);
    }

    public long count(String key, String status) {
        return get(key).rows().stream().filter(r -> status.equals(r.get("status"))).count();
    }

    public long total(String key) { return get(key).rows().size(); }

    public List<String> optionsFor(String key, String col) {
        if (col.equals("unit")) return get("units").rows().stream().map(r -> r.get("name")).toList();
        if (key.equals("maintenance") && col.equals("asset")) {
            List<String> names = new ArrayList<>();
            get("vehicles").rows().forEach(r -> names.add(r.get("name")));
            get("equipment").rows().forEach(r -> names.add(r.get("name")));
            return names;
        }
        List<String> o = get(key).options().get(col);
        return o == null ? List.of() : o;
    }

    // ---------- writing ----------

    public void save(String key, Map<String, String> form) {
        Module m = modules.get(key);
        String id = form.get("id");
        Map<String, String> row = (id == null || id.isBlank()) ? null : find(key, id);
        if (row == null) {
            row = new LinkedHashMap<>();
            row.put("id", String.valueOf(nextId++));
            if (m.defaultStatus() != null) row.put("status", m.defaultStatus());
            m.rows().add(row);
        }
        for (String c : m.formCols()) {
            if (form.containsKey(c)) row.put(c, form.get(c).trim());
        }
        recalc(key, row);
        if (key.equals("maintenance")) syncMaintenance(row);
    }

    public void delete(String key, String id) {
        get(key).rows().removeIf(r -> id.equals(r.get("id")));
    }

    public void setStatus(String key, String id, String value) {
        Map<String, String> row = find(key, id);
        if (row == null) return;
        row.put("status", value);
        recalc(key, row);
        if (key.equals("maintenance")) syncMaintenance(row);
    }

    private void recalc(String key, Map<String, String> row) {
        if (key.equals("supplies"))
            row.put("status", toInt(row.get("quantity")) < toInt(row.get("minLevel")) ? "LOW" : "OK");
    }

    // 'In progress' makes the asset unavailable; 'Completed' makes it available again
    private void syncMaintenance(Map<String, String> job) {
        String status = job.get("status");
        String target = "IN_PROGRESS".equals(status) ? "UNDER_MAINTENANCE"
                : "COMPLETED".equals(status) ? "AVAILABLE" : null;
        if (target == null) return;
        Map<String, String> asset = byName("vehicles", job.get("asset"));
        if (asset == null) asset = byName("equipment", job.get("asset"));
        if (asset != null) asset.put("status", target);
    }

    private Map<String, String> byName(String key, String name) {
        return get(key).rows().stream()
                .filter(r -> r.get("name") != null && r.get("name").equals(name))
                .findFirst().orElse(null);
    }

    // ---------- request workflow ----------

    public Result approve(String id) {
        Map<String, String> r = find("requests", id);
        if (r == null) return new Result(false, "Request not found");
        if (!"PENDING".equals(r.get("status"))) return new Result(false, "Only pending requests can be approved");
        r.put("status", "APPROVED");
        return new Result(true, "Request #" + id + " approved");
    }

    public Result reject(String id) {
        Map<String, String> r = find("requests", id);
        if (r == null) return new Result(false, "Request not found");
        String st = r.get("status");
        if (!"PENDING".equals(st) && !"APPROVED".equals(st))
            return new Result(false, "Request #" + id + " is already " + st);
        r.put("status", "REJECTED");
        r.put("remarks", "Rejected by approver");
        return new Result(true, "Request #" + id + " rejected");
    }

    public Result allocate(String id) {
        Map<String, String> r = find("requests", id);
        if (r == null) return new Result(false, "Request not found");
        String st = r.get("status");
        if (!"PENDING".equals(st) && !"APPROVED".equals(st))
            return new Result(false, "Request #" + id + " is already " + st);

        Map<String, String> v = firstAvailable("vehicles", r.get("vehicleType"));
        Map<String, String> e = firstAvailable("equipment", r.get("equipmentType"));
        if (v == null || e == null) {
            String why = v == null
                    ? "No available " + r.get("vehicleType") + " vehicle"
                    : "No available " + r.get("equipmentType") + " equipment";
            r.put("status", "REJECTED");
            r.put("remarks", why);
            return new Result(false, "Request #" + id + " rejected: " + why);
        }

        v.put("status", "ASSIGNED");
        e.put("status", "ASSIGNED");
        r.put("status", "ALLOCATED");
        r.put("remarks", v.get("name") + " + " + e.get("name") + " allocated");

        Map<String, String> a = new LinkedHashMap<>();
        a.put("id", String.valueOf(nextId++));
        a.put("request", "#" + id + " " + r.get("unit"));
        a.put("vehicle", v.get("name"));
        a.put("equipment", e.get("name"));
        a.put("date", LocalDate.now().toString());
        a.put("status", "ACTIVE");
        a.put("requestId", id);
        a.put("vehicleId", v.get("id"));
        a.put("equipmentId", e.get("id"));
        get("allocations").rows().add(a);

        return new Result(true, "Request #" + id + " allocated: " + v.get("name") + " and " + e.get("name"));
    }

    public Result complete(String id) {
        Map<String, String> r = find("requests", id);
        if (r == null) return new Result(false, "Request not found");
        if (!"ALLOCATED".equals(r.get("status")))
            return new Result(false, "Only allocated requests can be completed");

        get("allocations").rows().stream()
                .filter(a -> id.equals(a.get("requestId")) && "ACTIVE".equals(a.get("status")))
                .forEach(a -> {
                    a.put("status", "RELEASED");
                    Map<String, String> v = find("vehicles", a.get("vehicleId"));
                    if (v != null) v.put("status", "AVAILABLE");
                    Map<String, String> e = find("equipment", a.get("equipmentId"));
                    if (e != null) e.put("status", "AVAILABLE");
                });
        r.put("status", "COMPLETED");
        r.put("remarks", "Completed, resources released");
        return new Result(true, "Request #" + id + " completed and resources released");
    }

    private Map<String, String> firstAvailable(String key, String type) {
        return get(key).rows().stream()
                .filter(r -> "AVAILABLE".equals(r.get("status")) && type != null && type.equalsIgnoreCase(r.get("type")))
                .findFirst().orElse(null);
    }

    private int toInt(String s) {
        try { return Integer.parseInt(s.trim()); } catch (Exception e) { return 0; }
    }
}