package tfar.metalbarrels.util;

import tfar.metalbarrels.menu.MetalBarrelMenu;

public record BarrelProperties(int width, int height, BarrelMenuFactory barrelMenuFactory) {

    public static BarrelProperties copper = new BarrelProperties(5,9, MetalBarrelMenu::copperS);
    public static BarrelProperties iron = new BarrelProperties(6,9, MetalBarrelMenu::ironS);
    public static BarrelProperties silver = new BarrelProperties(8,9, MetalBarrelMenu::silverS);
    public static BarrelProperties gold = new BarrelProperties(9,9, MetalBarrelMenu::goldS);
    public static BarrelProperties diamond = new BarrelProperties(9,12, MetalBarrelMenu::diamondS);
    public static BarrelProperties netherite = new BarrelProperties(9,15, MetalBarrelMenu::netheriteS);

}
