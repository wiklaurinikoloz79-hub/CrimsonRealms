package com.crimsonrealms.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import com.crimsonrealms.util.ClockUtil;
import java.util.Map;

public class DigitalClockScreen extends Screen {
    private static final int CLOCK_WIDTH = 300;
    private static final int CLOCK_HEIGHT = 400;
    private static final int TITLE_COLOR = 0xFF4444;
    private static final int TEXT_COLOR = 0xFFFFFF;
    private static final int BACKGROUND_COLOR = 0x1a000080;
    
    public DigitalClockScreen() {
        super(Component.literal("Digital Clock"));
    }
    
    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics);
        
        int centerX = this.width / 2;
        int centerY = this.height / 2;
        int clockX = centerX - CLOCK_WIDTH / 2;
        int clockY = centerY - CLOCK_HEIGHT / 2;
        
        // Draw background
        guiGraphics.fill(clockX, clockY, clockX + CLOCK_WIDTH, clockY + CLOCK_HEIGHT, BACKGROUND_COLOR);
        
        // Draw border
        guiGraphics.fill(clockX - 2, clockY - 2, clockX + CLOCK_WIDTH + 2, clockY + 2, TITLE_COLOR);
        guiGraphics.fill(clockX - 2, clockY + CLOCK_HEIGHT, clockX + CLOCK_WIDTH + 2, clockY + CLOCK_HEIGHT + 2, TITLE_COLOR);
        guiGraphics.fill(clockX - 2, clockY, clockX, clockY + CLOCK_HEIGHT, TITLE_COLOR);
        guiGraphics.fill(clockX + CLOCK_WIDTH, clockY, clockX + CLOCK_WIDTH + 2, clockY + CLOCK_HEIGHT, TITLE_COLOR);
        
        // Draw title
        drawCenteredString(guiGraphics, this.font, "⏰ Crimson Clock ⏰", centerX, clockY + 15, TITLE_COLOR);
        
        // Get all timezone data
        Map<String, String> allTimes = ClockUtil.getAllTimesFormatted();
        
        int yOffset = clockY + 45;
        int lineHeight = 22;
        
        for (Map.Entry<String, String> entry : allTimes.entrySet()) {
            String timezone = entry.getKey();
            String time = entry.getValue();
            
            // Draw timezone label
            drawString(guiGraphics, this.font, timezone + ":", clockX + 20, yOffset, 0xFFAAAA);
            
            // Draw time
            drawString(guiGraphics, this.font, time, clockX + 80, yOffset, TEXT_COLOR);
            
            yOffset += lineHeight;
        }
        
        // Draw footer
        drawCenteredString(guiGraphics, this.font, "Press ESC to close", centerX, clockY + CLOCK_HEIGHT - 15, 0xFF8888);
        
        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }
    
    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 256) { // ESC key
            this.onClose();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }
    
    @Override
    public void onClose() {
        this.minecraft.setScreen(null);
    }
    
    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
