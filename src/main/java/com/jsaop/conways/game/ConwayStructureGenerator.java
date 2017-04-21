package com.jsaop.conways.game;

public class ConwayStructureGenerator {

    public static final String GLIDER_GUN = createGliderGunString();
    public static final String R_PENTOMINO = createRPentomino();
    public static final String BLOCK_LAYING_SWITCH = createBlockLayingSwitchString();

    private Game game;

    public ConwayStructureGenerator(Game game) {
        this.game = game;
    }
    
    public void spawnFromString(String pattern){
        spawnFromStringAtOffset(pattern, 0, 0);
    }

    public void spawnFromStringAtOffset(String pattern, int xOffset, int yOffset) {
        int rowIndex = xOffset;
        int colIndex = yOffset;

        for (char c : pattern.toCharArray()) {
            switch (c) {
                case '.':
                    rowIndex++;
                    break;
                case '*':
                    game.spawn(rowIndex, colIndex);
                    rowIndex++;
                    break;
                case '\n':
                    colIndex++;
                    rowIndex = xOffset;
                    break;
                default:
                    break;
            }
        }

    }
    private static String createRPentomino() {
        StringBuilder sb = new StringBuilder();

        sb.append(".\n");
        sb.append("..**\n");
        sb.append(".**\n");
        sb.append("..*\n");
        sb.append(".\n");

        return sb.toString();
    }

    private static String createGliderGunString() {
        StringBuilder sb = new StringBuilder();
        sb.append(
                "........................*\n" +
                "......................*.*\n" +
                "............**......**............**\n" +
                "...........*...*....**............**\n" +
                "**........*.....*...**\n" +
                "**........*...*.**....*.*\n" +
                "..........*.....*.......*\n" +
                "...........*...*\n" +
                "............**"
        );
        return sb.toString();
    }

    private static String createBlockLayingSwitchString() {
        StringBuilder sb = new StringBuilder();
        sb.append("......*.\n");
        sb.append("....*.**\n");
        sb.append("....*.*.\n");
        sb.append("....*...\n");
        sb.append("..*.....\n");
        sb.append("*.*.....\n");
        return sb.toString();
    }

}
