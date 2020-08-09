package io.github.darkkronicle.polish.gui.widgets;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import lombok.Getter;
import lombok.Setter;
import io.github.darkkronicle.polish.util.ColorUtil;
import io.github.darkkronicle.polish.util.Colors;
import io.github.darkkronicle.polish.util.EasingFunctions;
import io.github.darkkronicle.polish.util.SimpleColor;
import net.minecraft.SharedConstants;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;


/**
 * Textbox button. Based off of Minecraft's {@link net.minecraft.client.gui.widget.TextFieldWidget}
 */
public class TextboxButton extends AbstractPWidget {

    private final TextRenderer textRenderer;
    private String text;
    private int maxLength;
    private int focusedTicks;
    @Getter @Setter
    private boolean focused = false;
    private boolean wasfocused;
    private boolean focusUnlocked;
    private boolean editable;
    private boolean selecting;
    private int firstCharacterIndex;
    private int selectionStart;
    private int selectionEnd;
    private int editableColor;
    private int uneditableColor;
    private float scale = 0.5F;
    private String suggestion;
    private Consumer<String> changedListener;
    private Predicate<String> textPredicate;
    private BiFunction<String, Integer, String> renderTextProvider;
    private boolean showSelectOutline;

    private SimpleColor backgroundColor;

    private int selectAnim = 500;
    private float selectStart = -1;
    private int flickerAnim = 500;
    private float flickerStart = -1;

    public TextboxButton(TextRenderer textRenderer, int x, int y, int width, int height, SimpleColor backgroundColor, boolean showSelectOutline) {
        super(x, y, width, height);
        this.text = "";
        this.maxLength = 32;
        this.focused = false;
        this.focusUnlocked = true;
        this.editable = true;
        this.editableColor = 14737632;
        this.uneditableColor = 7368816;
        this.textPredicate = Objects::nonNull;
        this.renderTextProvider = (string, integer) -> string;
        this.textRenderer = textRenderer;
        this.backgroundColor = backgroundColor;
        this.showSelectOutline = showSelectOutline;
    }

    public void setChangedListener(Consumer<String> changedListener) {
        this.changedListener = changedListener;
    }

    public void setRenderTextProvider(BiFunction<String, Integer, String> renderTextProvider) {
        this.renderTextProvider = renderTextProvider;
    }

    public void tick() {
        ++this.focusedTicks;
    }

    @Override
    public boolean isTickable() {
        return true;
    }

    public void setText(String text) {
        if (this.textPredicate.test(text)) {
            if (text.length() > this.maxLength) {
                this.text = text.substring(0, this.maxLength);
            } else {
                this.text = text;
            }

            this.setCursorToEnd();
            this.setSelectionEnd(this.selectionStart);
            this.onChanged(text);
        }
    }

    public String getText() {
        return this.text;
    }

    public String getSelectedText() {
        int i = Math.min(this.selectionStart, this.selectionEnd);
        int j = Math.max(this.selectionStart, this.selectionEnd);
        return this.text.substring(i, j);
    }

    public void setTextPredicate(Predicate<String> textPredicate) {
        this.textPredicate = textPredicate;
    }

    public void write(String string) {
        int i = Math.min(this.selectionStart, this.selectionEnd);
        int j = Math.max(this.selectionStart, this.selectionEnd);
        int k = this.maxLength - this.text.length() - (i - j);
        String string2 = SharedConstants.stripInvalidChars(string);
        int l = string2.length();
        if (k < l) {
            string2 = string2.substring(0, k);
            l = k;
        }

        String string3 = (new StringBuilder(this.text)).replace(i, j, string2).toString();
        if (this.textPredicate.test(string3)) {
            this.text = string3;
            this.setSelectionStart(i + l);
            this.setSelectionEnd(this.selectionStart);
            this.onChanged(this.text);
        }
    }

    private void onChanged(String newText) {
        if (this.changedListener != null) {
            this.changedListener.accept(newText);
        }
    }

    private void erase(int offset) {
        if (Screen.hasControlDown()) {
            this.eraseWords(offset);
        } else {
            this.eraseCharacters(offset);
        }

    }

    public void eraseWords(int wordOffset) {
        if (!this.text.isEmpty()) {
            if (this.selectionEnd != this.selectionStart) {
                this.write("");
            } else {
                this.eraseCharacters(this.getWordSkipPosition(wordOffset) - this.selectionStart);
            }
        }
    }

    public void eraseCharacters(int characterOffset) {
        if (!this.text.isEmpty()) {
            if (this.selectionEnd != this.selectionStart) {
                this.write("");
            } else {
                int i = this.method_27537(characterOffset);
                int j = Math.min(i, this.selectionStart);
                int k = Math.max(i, this.selectionStart);
                if (j != k) {
                    String string = (new StringBuilder(this.text)).delete(j, k).toString();
                    if (this.textPredicate.test(string)) {
                        this.text = string;
                        this.setCursor(j);
                    }
                }
            }
        }
    }

    public int getWordSkipPosition(int wordOffset) {
        return this.getWordSkipPosition(wordOffset, this.getCursor());
    }

    private int getWordSkipPosition(int wordOffset, int cursorPosition) {
        return this.getWordSkipPosition(wordOffset, cursorPosition, true);
    }

    private int getWordSkipPosition(int wordOffset, int cursorPosition, boolean skipOverSpaces) {
        int i = cursorPosition;
        boolean bl = wordOffset < 0;
        int j = Math.abs(wordOffset);

        for(int k = 0; k < j; ++k) {
            if (!bl) {
                int l = this.text.length();
                i = this.text.indexOf(32, i);
                if (i == -1) {
                    i = l;
                } else {
                    while(skipOverSpaces && i < l && this.text.charAt(i) == ' ') {
                        ++i;
                    }
                }
            } else {
                while(skipOverSpaces && i > 0 && this.text.charAt(i - 1) == ' ') {
                    --i;
                }

                while(i > 0 && this.text.charAt(i - 1) != ' ') {
                    --i;
                }
            }
        }

        return i;
    }

    public void moveCursor(int offset) {
        this.setCursor(this.method_27537(offset));
    }

    private int method_27537(int i) {
        return Util.moveCursor(this.text, this.selectionStart, i);
    }

    public void setCursor(int cursor) {
        this.setSelectionStart(cursor);
        if (!this.selecting) {
            this.setSelectionEnd(this.selectionStart);
        }

        this.onChanged(this.text);
    }

    public void setSelectionStart(int cursor) {
        this.selectionStart = MathHelper.clamp(cursor, 0, this.text.length());
    }

    public void setCursorToStart() {
        this.setCursor(0);
    }

    public void setCursorToEnd() {
        this.setCursor(this.text.length());
    }

    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (!this.isActive()) {
            return false;
        } else {
            this.selecting = Screen.hasShiftDown();
            if (Screen.isSelectAll(keyCode)) {
                this.setCursorToEnd();
                this.setSelectionEnd(0);
                return true;
            } else if (Screen.isCopy(keyCode)) {
                MinecraftClient.getInstance().keyboard.setClipboard(this.getSelectedText());
                return true;
            } else if (Screen.isPaste(keyCode)) {
                if (this.editable) {
                    this.write(MinecraftClient.getInstance().keyboard.getClipboard());
                }

                return true;
            } else if (Screen.isCut(keyCode)) {
                MinecraftClient.getInstance().keyboard.setClipboard(this.getSelectedText());
                if (this.editable) {
                    this.write("");
                }

                return true;
            } else {
                switch(keyCode) {
                    case 259:
                        if (this.editable) {
                            this.selecting = false;
                            this.erase(-1);
                            this.selecting = Screen.hasShiftDown();
                        }

                        return true;
                    case 260:
                    case 264:
                    case 265:
                    case 266:
                    case 267:
                    default:
                        return false;
                    case 261:
                        if (this.editable) {
                            this.selecting = false;
                            this.erase(1);
                            this.selecting = Screen.hasShiftDown();
                        }

                        return true;
                    case 262:
                        if (Screen.hasControlDown()) {
                            this.setCursor(this.getWordSkipPosition(1));
                        } else {
                            this.moveCursor(1);
                        }

                        return true;
                    case 263:
                        if (Screen.hasControlDown()) {
                            this.setCursor(this.getWordSkipPosition(-1));
                        } else {
                            this.moveCursor(-1);
                        }

                        return true;
                    case 268:
                        this.setCursorToStart();
                        return true;
                    case 269:
                        this.setCursorToEnd();
                        return true;
                }
            }
        }
    }

    public boolean isActive() {
        return this.isVisible() && this.isFocused() && this.isEditable();
    }

    public boolean charTyped(char chr, int keyCode) {
        if (!this.isActive()) {
            return false;
        } else if (SharedConstants.isValidChar(chr)) {
            if (this.editable) {
                this.write(Character.toString(chr));
            }

            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean onClick(double mouseX, double mouseY, int button) {
        if (!this.isVisible()) {
            return false;
        } else {
            boolean bl = mouseX >= (double)this.getAbsoluteX() && mouseX < (double)(this.getAbsoluteX() + this.width) && mouseY >= (double)this.getAbsoluteY() && mouseY < (double)(this.getAbsoluteY() + this.height);
            if (this.focusUnlocked) {
                this.setSelected(bl);
            }

            if (this.isFocused() && bl && button == 0) {
                int i = MathHelper.floor(mouseX) - this.getAbsoluteX();
                if (this.focused) {
                    i -= 4;
                }

                String string = this.textRenderer.trimToWidth(this.text.substring(this.firstCharacterIndex), this.getInnerWidth());
                this.setCursor(this.textRenderer.trimToWidth(string, i).length() + this.firstCharacterIndex);
                return true;
            } else {
                return false;
            }
        }
    }

    public void setSelected(boolean selected) {
        focused = selected;
    }

    @Override
    public void renderWidget(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        matrices.push();
        int textColor;
        int scaledWidth = Math.round(width / scale);
        int scaledHeight = Math.round(height / scale);
        int scaledX = Math.round(getAbsoluteX() / scale);
        int scaledY = Math.round(getAbsoluteY() / scale);
        matrices.scale(scale, scale, 1);
        if (showSelectOutline) {
            if (isHovered() != isWasHovered() && !isFocused()) {
                selectStart = Util.getMeasuringTimeMs();
            } else if (isFocused() != wasfocused && !isHovered()) {
                selectStart = Util.getMeasuringTimeMs();
            }
        } else {
            if (isHovered() != isWasHovered()) {
                selectStart = Util.getMeasuringTimeMs();
            }
        }

        SimpleColor outline = Colors.WHITE.color();
        double selectTrans = selectStart == -1 ? 1 : MathHelper.clamp((Util.getMeasuringTimeMs() - selectStart) / selectAnim, 0, 1);

        fillRoundedRect(matrices, scaledX, scaledY, scaledWidth, scaledHeight, 5,backgroundColor.color());
        if (this.isHovered() || isFocused()) {
            outline = ColorUtil.blend(backgroundColor, outline, (float) EasingFunctions.Types.SINE_IN_OUT.apply(selectTrans));
            outlineRoundedRect(matrices, scaledX - 1, scaledY - 1, scaledWidth + 1, scaledHeight + 1, 5, outline.color());
        } else if (selectTrans != -1) {
            outline = ColorUtil.blend(outline, backgroundColor, (float) EasingFunctions.Types.SINE_IN_OUT.apply(selectTrans));
            outlineRoundedRect(matrices, scaledX - 1, scaledY - 1, scaledWidth + 1, scaledHeight + 1, 5, outline.color());
        }
        matrices.scale(1 / scale, 1 / scale, 1);

        textColor = this.editable ? this.editableColor : this.uneditableColor;
        int startSelectionOffset = this.selectionStart - this.firstCharacterIndex;
        int endSelectionOffset = this.selectionEnd - this.firstCharacterIndex;
        String trimmedText = this.textRenderer.trimToWidth(this.text.substring(this.firstCharacterIndex), this.getInnerWidth());
        boolean isSelection = startSelectionOffset >= 0 && startSelectionOffset <= trimmedText.length();

        int focusY = getAbsoluteY() + (height - 8) / 2;
        int focusX = getAbsoluteX() + 4;
        int shadowX = focusX;
        if (endSelectionOffset > trimmedText.length()) {
            endSelectionOffset = trimmedText.length();
        }

        if (!trimmedText.isEmpty()) {
            String selectedText = isSelection ? trimmedText.substring(0, startSelectionOffset) : trimmedText;
            shadowX = this.textRenderer.drawWithShadow(matrices, this.renderTextProvider.apply(selectedText, this.firstCharacterIndex), (float)focusX, (float)focusY, textColor);
        }

        boolean tooBig = this.selectionStart < this.text.length() || this.text.length() >= this.getMaxLength();

        int selctionX = shadowX;
        if (!isSelection) {
            selctionX = startSelectionOffset > 0 ? focusX + this.width : focusX;
        } else if (tooBig) {
            selctionX = shadowX - 1;
            --shadowX;
        }

        if (!trimmedText.isEmpty() && isSelection && startSelectionOffset < trimmedText.length()) {
            this.textRenderer.drawWithShadow(matrices, this.renderTextProvider.apply(trimmedText.substring(startSelectionOffset), this.selectionStart), (float)shadowX, (float)focusY, textColor);
        }


        if (isFocused() && ((Util.getMeasuringTimeMs() - flickerStart) / flickerAnim > 2 || flickerStart == -1)) {
            flickerStart = Util.getMeasuringTimeMs();
        }

        double flickerTime = flickerStart == -1 ? 1 : (Util.getMeasuringTimeMs() - flickerStart) / flickerAnim;

        double flickertrans = flickerTime <= 1 ? MathHelper.clamp(flickerTime, 0, 1) : MathHelper.clamp(1 - (flickerTime - 1), 0, 1);

        if (!tooBig && this.suggestion != null) {
            this.textRenderer.drawWithShadow(matrices, this.suggestion, (float)(selctionX - 1), (float)focusY, -8355712);
        }

        int y1;
        int width;
        int height;


        if (isFocused()) {
            SimpleColor flickerColor = ColorUtil.blend(Colors.WHITE.color().withAlpha(10), Colors.WHITE.color(), (float) EasingFunctions.Types.QUINT_IN_OUT.apply(flickertrans));
            if (!tooBig) {
                // Cursor is at the end.
                this.textRenderer.drawWithShadow(matrices, "_", (float) selctionX, (float) focusY, flickerColor.color());
            } else {
                // Cursor is in the middle
                rect(matrices, selctionX, focusY - 1, 1, 11, flickerColor.color());
            }
        }

        if (endSelectionOffset != startSelectionOffset) {
            int highlightWidth = focusX + this.textRenderer.getWidth(trimmedText.substring(0, endSelectionOffset));
            y1 = focusY - 1;
            width = highlightWidth - 1;
            height = focusY + 1;
            this.drawSelectionHighlight(selctionX, y1, width, height + 9);
        }
        wasfocused = focused;
        matrices.pop();
    }

    private void drawSelectionHighlight(int x1, int y1, int x2, int y2) {
        int j;
        if (x1 < x2) {
            j = x1;
            x1 = x2;
            x2 = j;
        }

        if (y1 < y2) {
            j = y1;
            y1 = y2;
            y2 = j;
        }

        if (x2 > this.getAbsoluteX() + this.width) {
            x2 = this.getAbsoluteX() + this.width;
        }

        if (x1 > this.getAbsoluteX() + this.width) {
            x1 = this.getAbsoluteX() + this.width;
        }

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        RenderSystem.color4f(0.0F, 0.0F, 255.0F, 255.0F);
        RenderSystem.disableTexture();
        RenderSystem.enableColorLogicOp();
        RenderSystem.logicOp(GlStateManager.LogicOp.OR_REVERSE);
        bufferBuilder.begin(7, VertexFormats.POSITION);
        bufferBuilder.vertex((double)x1, (double)y2, 0.0D).next();
        bufferBuilder.vertex((double)x2, (double)y2, 0.0D).next();
        bufferBuilder.vertex((double)x2, (double)y1, 0.0D).next();
        bufferBuilder.vertex((double)x1, (double)y1, 0.0D).next();
        tessellator.draw();
        RenderSystem.disableColorLogicOp();
        RenderSystem.enableTexture();
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
        if (this.text.length() > maxLength) {
            this.text = this.text.substring(0, maxLength);
            this.onChanged(this.text);
        }

    }

    private int getMaxLength() {
        return this.maxLength;
    }

    public int getCursor() {
        return this.selectionStart;
    }

    private boolean hasBorder() {
        return this.focused;
    }

    public void setHasBorder(boolean hasBorder) {
        this.focused = hasBorder;
    }

    public void setEditableColor(int color) {
        this.editableColor = color;
    }

    public void setUneditableColor(int color) {
        this.uneditableColor = color;
    }

    public boolean changeFocus(boolean lookForwards) {
        return this.editable ? super.changeFocus(lookForwards) : false;
    }

    public boolean isMouseOver(double mouseX, double mouseY) {
        return mouseX >= (double)this.getAbsoluteX() && mouseX < (double)(this.getAbsoluteX() + this.width) && mouseY >= (double)this.getAbsoluteY() && mouseY < (double)(this.getAbsoluteY() + this.height);
    }

    private boolean isEditable() {
        return this.editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public int getInnerWidth() {
        return this.hasBorder() ? this.width - 8 : this.width;
    }

    public void setSelectionEnd(int i) {
        int j = this.text.length();
        this.selectionEnd = MathHelper.clamp(i, 0, j);
        if (this.textRenderer != null) {
            if (this.firstCharacterIndex > j) {
                this.firstCharacterIndex = j;
            }

            int k = this.getInnerWidth();
            String string = this.textRenderer.trimToWidth(this.text.substring(this.firstCharacterIndex), k);
            int l = string.length() + this.firstCharacterIndex;
            if (this.selectionEnd == this.firstCharacterIndex) {
                this.firstCharacterIndex -= this.textRenderer.trimToWidth(this.text, k, true).length();
            }

            if (this.selectionEnd > l) {
                this.firstCharacterIndex += this.selectionEnd - l;
            } else if (this.selectionEnd <= this.firstCharacterIndex) {
                this.firstCharacterIndex -= this.firstCharacterIndex - this.selectionEnd;
            }

            this.firstCharacterIndex = MathHelper.clamp(this.firstCharacterIndex, 0, j);
        }

    }

    public void setFocusUnlocked(boolean focusUnlocked) {
        this.focusUnlocked = focusUnlocked;
    }

    public boolean isVisible() {
        return true;
    }

    public void setSuggestion(@Nullable String suggestion) {
        this.suggestion = suggestion;
    }

    public int getCharacterX(int index) {
        return index > this.text.length() ? this.getAbsoluteY() : this.getAbsoluteY() + this.textRenderer.getWidth(this.text.substring(0, index));
    }
}
