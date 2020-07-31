package net.darkkronicle.polish.impl;

import net.darkkronicle.polish.api.ScissorsHelper;
import net.darkkronicle.polish.util.SimpleRectangle;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Window;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

@Environment(EnvType.CLIENT)
public class ScissorsHelperImpl implements ScissorsHelper {

    private ArrayList<SimpleRectangle> scissors;

    public ScissorsHelperImpl() {
        scissors = new ArrayList<>();

    }

    @Override
    public void addScissor(SimpleRectangle rect) {
        scissors.add(rect);
        apply();
    }

    @Override
    public void removeLastScissor() {
        if (!scissors.isEmpty()) {
            scissors.remove(scissors.size() -1);
        }
        apply();
    }

    @Override
    public void apply() {
        if (!scissors.isEmpty()) {
            SimpleRectangle rect = scissors.get(0);
            Window window = MinecraftClient.getInstance().getWindow();
            double scale = window.getScaleFactor();
            GL11.glEnable(GL11.GL_SCISSOR_TEST);
            GL11.glScissor((int) (rect.x() * scale), (int) ((window.getScaledHeight() - rect.height() - rect.y()) * scale), (int) (rect.width() * scale), (int) (rect.height() * scale));
        } else {
            GL11.glDisable(GL11.GL_SCISSOR_TEST);
        }
    }
}
