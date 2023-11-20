package knu.cse.locker.manager.locker.entity;

public enum LockerLocation {
    LOC_B1, LOC_L, LOC_3F;

    public String getName(LockerLocation loc) {
        return switch (loc) {
            case LOC_B1 -> "B1";
            case LOC_L -> "L";
            case LOC_3F -> "3F";
        };
    }

    public LockerLocation getLocation(String name) {
        String loc_str = name.split("-")[0];

        return switch (loc_str) {
            case "B1" -> LockerLocation.LOC_B1;
            case "L" -> LockerLocation.LOC_L;
            case "3F" -> LockerLocation.LOC_3F;
            default -> null;
        };

    }
}
