package main.ui.components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Bouton personnalisé avec style moderne.
 */
public class ModernButton extends JButton {
    private Color backgroundColor;
    private Color hoverColor;
    private Color pressedColor;
    
    public ModernButton(String text, Color baseColor) {
        super(text);
        this.backgroundColor = baseColor;
        this.hoverColor = darkenColor(baseColor, 0.1f);
        this.pressedColor = darkenColor(baseColor, 0.2f);
        
        initializeButton();
    }
    
    private void initializeButton() {
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setOpaque(true);
        setForeground(Color.WHITE);
        setFont(new Font("Segoe UI", Font.BOLD, 14));
        setBorder(new EmptyBorder(10, 20, 10, 20));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        setBackground(backgroundColor);
        
        // Effets de survol
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(hoverColor);
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(backgroundColor);
            }
            
            @Override
            public void mousePressed(MouseEvent e) {
                setBackground(pressedColor);
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                setBackground(hoverColor);
            }
        });
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Fond arrondi
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
        
        // Texte
        g2.setColor(getForeground());
        FontMetrics fm = g2.getFontMetrics();
        int textX = (getWidth() - fm.stringWidth(getText())) / 2;
        int textY = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
        g2.drawString(getText(), textX, textY);
        
        g2.dispose();
    }
    
    private Color darkenColor(Color color, float factor) {
        return new Color(
            Math.max((int)(color.getRed() * (1 - factor)), 0),
            Math.max((int)(color.getGreen() * (1 - factor)), 0),
            Math.max((int)(color.getBlue() * (1 - factor)), 0)
        );
    }
}