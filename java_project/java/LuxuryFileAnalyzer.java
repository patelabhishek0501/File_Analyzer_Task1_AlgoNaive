import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.FlatClientProperties;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class LuxuryFileAnalyzer extends JFrame {

    private JLabel fileLabel, wordsLabel, charsLabel, linesLabel, sizeLabel;
    private JButton themeButton;
    private AnimatedDropZone dropZone;
    private AnimatedBackground backgroundPanel;
    private boolean darkMode = false;

    private final Color PRIMARY = new Color(0, 122, 255);

    public LuxuryFileAnalyzer() {
        setTitle("Luxury File Analyzer");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);

        UIManager.put("defaultFont", new Font("SF Pro Display", Font.PLAIN, 16));

        // ------------------- Animated Background -------------------
        backgroundPanel = new AnimatedBackground();
        backgroundPanel.setLayout(new BorderLayout());

        // ------------------- Toolbar -------------------
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBorder(new EmptyBorder(15, 20, 15, 20));
        topBar.setOpaque(false);

        JLabel title = new JLabel("📂 Luxury File Analyzer");
        title.setFont(new Font("SF Pro Display", Font.BOLD, 22));
        title.setForeground(new Color(240, 240, 240));
        topBar.add(title, BorderLayout.WEST);

        themeButton = new JButton("☀ Light");
        themeButton.putClientProperty(FlatClientProperties.BUTTON_TYPE, "roundRect");
        themeButton.addActionListener(this::toggleTheme);
        topBar.add(themeButton, BorderLayout.EAST);

        // ------------------- Animated Drop Zone -------------------
        dropZone = new AnimatedDropZone();
        dropZone.setPreferredSize(new Dimension(400, 400));
        dropZone.setBrowseClickListener(this::chooseFile);

        // Enable Drag & Drop
        new DropTarget(dropZone, new DropTargetListener() {
            public void dragEnter(DropTargetDragEvent e) {
                dropZone.setDragging(true);
            }

            public void dragOver(DropTargetDragEvent e) {}

            public void dropActionChanged(DropTargetDragEvent e) {}

            public void dragExit(DropTargetEvent e) {
                dropZone.setDragging(false);
            }

            public void drop(DropTargetDropEvent e) {
                dropZone.setDragging(false);
                try {
                    e.acceptDrop(DnDConstants.ACTION_COPY);
                    List<File> droppedFiles = (List<File>) e.getTransferable()
                            .getTransferData(DataFlavor.javaFileListFlavor);
                    if (!droppedFiles.isEmpty()) {
                        File file = droppedFiles.get(0);
                        processFile(file);
                    }
                    e.dropComplete(true);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    e.dropComplete(false);
                }
            }
        });

        // ------------------- File Info Label -------------------
        fileLabel = createInfoLabel("No file selected");
        fileLabel.setFont(new Font("SF Pro Display", Font.BOLD, 18));
        fileLabel.setForeground(new Color(240, 240, 240));

        // ------------------- Stats Cards -------------------
        wordsLabel = createInfoLabel("0");
        charsLabel = createInfoLabel("0");
        linesLabel = createInfoLabel("0");
        sizeLabel = createInfoLabel("0 KB");

        JPanel statsPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        statsPanel.setOpaque(false);
        statsPanel.add(createCard("📝 Words", wordsLabel));
        statsPanel.add(createCard("🔠 Characters", charsLabel));
        statsPanel.add(createCard("📑 Lines", linesLabel));
        statsPanel.add(createCard("📊 File Size", sizeLabel));

        // ------------------- Center Panel -------------------
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(new EmptyBorder(30, 50, 30, 50));
        centerPanel.setOpaque(false);
        
        JPanel dropZoneWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        dropZoneWrapper.setOpaque(false);
        dropZoneWrapper.add(dropZone);
        
        centerPanel.add(dropZoneWrapper);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        centerPanel.add(fileLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        centerPanel.add(statsPanel);

        backgroundPanel.add(topBar, BorderLayout.NORTH);
        backgroundPanel.add(centerPanel, BorderLayout.CENTER);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(backgroundPanel, BorderLayout.CENTER);
    }

    // ------------------- Animated Background Component -------------------
    class AnimatedBackground extends JPanel {
        private List<Particle> particles;
        private Timer animationTimer;
        private float waveOffset = 0;

        class Particle {
            float x, y, size, speedX, speedY, opacity;
            
            Particle(int w, int h) {
                Random rand = new Random();
                x = rand.nextInt(w);
                y = rand.nextInt(h);
                size = 2 + rand.nextFloat() * 4;
                speedX = -0.5f + rand.nextFloat() * 1;
                speedY = -0.5f + rand.nextFloat() * 1;
                opacity = 0.3f + rand.nextFloat() * 0.4f;
            }
            
            void update(int w, int h) {
                x += speedX;
                y += speedY;
                
                if (x < 0) x = w;
                if (x > w) x = 0;
                if (y < 0) y = h;
                if (y > h) y = 0;
            }
        }

        public AnimatedBackground() {
            particles = new ArrayList<>();
            
            // Create particles after component is displayed
            SwingUtilities.invokeLater(() -> {
                int w = getWidth();
                int h = getHeight();
                if (w > 0 && h > 0) {
                    for (int i = 0; i < 50; i++) {
                        particles.add(new Particle(w, h));
                    }
                }
            });
            
            animationTimer = new Timer(50, e -> {
                waveOffset += 0.05f;
                for (Particle p : particles) {
                    p.update(getWidth(), getHeight());
                }
                repaint();
            });
            animationTimer.start();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth();
            int h = getHeight();

            // Dark gradient background
            GradientPaint bgGradient = new GradientPaint(
                0, 0, new Color(15, 32, 39),
                w, h, new Color(32, 58, 67)
            );
            g2.setPaint(bgGradient);
            g2.fillRect(0, 0, w, h);

            // Animated wave effect
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.15f));
            
            // Multiple wave layers
            for (int layer = 0; layer < 3; layer++) {
                Path2D wave = new Path2D.Float();
                wave.moveTo(0, h);
                
                float amplitude = 40 + layer * 15;
                float frequency = 0.008f - layer * 0.001f;
                float offset = waveOffset + layer * 2;
                
                for (int x = 0; x <= w; x += 5) {
                    float y = h - 150 - layer * 50 + 
                             (float) (Math.sin(x * frequency + offset) * amplitude);
                    wave.lineTo(x, y);
                }
                
                wave.lineTo(w, h);
                wave.closePath();
                
                Color waveColor = new Color(0, 150, 200, 40 - layer * 10);
                g2.setColor(waveColor);
                g2.fill(wave);
            }

            // Draw particles
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
            for (Particle p : particles) {
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, p.opacity));
                g2.setColor(new Color(100, 200, 255));
                g2.fill(new Ellipse2D.Float(p.x, p.y, p.size, p.size));
                
                // Connect nearby particles with lines
                for (Particle other : particles) {
                    float dist = (float) Math.sqrt(Math.pow(p.x - other.x, 2) + Math.pow(p.y - other.y, 2));
                    if (dist < 100 && dist > 0) {
                        float alpha = (100 - dist) / 100 * 0.3f;
                        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
                        g2.setStroke(new BasicStroke(1));
                        g2.setColor(new Color(100, 200, 255));
                        g2.draw(new Line2D.Float(p.x, p.y, other.x, other.y));
                    }
                }
            }

            // Bottom text effect
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f));
            g2.setFont(new Font("SF Pro Display", Font.BOLD, 60));
            g2.setColor(new Color(255, 255, 255, 30));
            String text = "Drag & drop. It's online.";
            FontMetrics fm = g2.getFontMetrics();
            int textWidth = fm.stringWidth(text);
            g2.drawString(text, (w - textWidth) / 2, h - 50);

            g2.dispose();
        }
    }

    // ------------------- Animated Drop Zone Component -------------------
    class AnimatedDropZone extends JPanel {
        private boolean isDragging = false;
        private float dashOffset = 0;
        private Timer animationTimer;
        private ActionListener browseListener;

        public AnimatedDropZone() {
            setOpaque(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            
            animationTimer = new Timer(50, e -> {
                dashOffset += 2;
                if (dashOffset > 20) dashOffset = 0;
                repaint();
            });
            animationTimer.start();

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (browseListener != null) {
                        browseListener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
                    }
                }
            });
        }

        public void setDragging(boolean dragging) {
            this.isDragging = dragging;
            repaint();
        }

        public void setBrowseClickListener(ActionListener listener) {
            this.browseListener = listener;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth();
            int h = getHeight();
            int size = Math.min(w, h) - 40;
            int x = (w - size) / 2;
            int y = (h - size) / 2;

            // Background circle with glow
            if (isDragging) {
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
                g2.setColor(new Color(100, 181, 246));
                g2.fillOval(x - 10, y - 10, size + 20, size + 20);
            }
            
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.9f));
            g2.setColor(new Color(255, 255, 255, isDragging ? 250 : 230));
            g2.fillOval(x, y, size, size);

            // Animated dashed border
            float[] dash = {15f, 10f};
            BasicStroke dashed = new BasicStroke(4.0f, BasicStroke.CAP_ROUND, 
                                                 BasicStroke.JOIN_ROUND, 10.0f, dash, dashOffset);
            g2.setStroke(dashed);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
            g2.setColor(isDragging ? new Color(33, 150, 243) : new Color(77, 208, 225));
            g2.drawOval(x, y, size, size);

            // Folder icon
            int iconSize = 80;
            int iconX = w / 2 - iconSize / 2;
            int iconY = h / 2 - 80;
            
            g2.setColor(new Color(100, 181, 246));
            g2.fillRoundRect(iconX, iconY, 40, 15, 5, 5);
            g2.fillRoundRect(iconX, iconY + 10, iconSize, 60, 10, 10);

            // Text
            g2.setColor(isDragging ? new Color(33, 150, 243) : new Color(50, 50, 50));
            g2.setFont(new Font("SF Pro Display", Font.BOLD, 20));
            String text1 = "Drag and drop your project";
            FontMetrics fm = g2.getFontMetrics();
            int textWidth = fm.stringWidth(text1);
            g2.drawString(text1, w / 2 - textWidth / 2, h / 2 + 20);

            String text2 = "folder here.";
            textWidth = fm.stringWidth(text2);
            g2.drawString(text2, w / 2 - textWidth / 2, h / 2 + 45);

            // "Or, browse to upload"
            g2.setFont(new Font("SF Pro Display", Font.PLAIN, 14));
            g2.setColor(Color.GRAY);
            String orText = "Or, ";
            FontMetrics fm2 = g2.getFontMetrics();
            int orWidth = fm2.stringWidth(orText);
            int orX = w / 2 - 45;
            g2.drawString(orText, orX, h / 2 + 75);

            g2.setColor(new Color(0, 122, 255));
            g2.setFont(new Font("SF Pro Display", Font.BOLD, 14));
            String browseText = "browse to upload";
            int browseWidth = fm2.stringWidth(browseText);
            g2.drawString(browseText, orX + orWidth, h / 2 + 75);
            g2.drawLine(orX + orWidth, h / 2 + 77, orX + orWidth + browseWidth, h / 2 + 77);

            g2.dispose();
        }
    }

    // ------------------- Card Creation -------------------
    private JPanel createCard(String title, JLabel valueLabel) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(new EmptyBorder(20, 20, 20, 20));
        card.putClientProperty(FlatClientProperties.STYLE,
                "arc:20; background:#FFFFFFDD; borderWidth:0; shadowWidth:12; shadowOpacity:0.15;");
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("SF Pro Display", Font.BOLD, 16));
        valueLabel.setFont(new Font("SF Pro Display", Font.BOLD, 24));
        valueLabel.setHorizontalAlignment(SwingConstants.CENTER);
        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);

        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                animateCardHover(card, true);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                animateCardHover(card, false);
            }
        });

        return card;
    }

    private JLabel createInfoLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("SF Pro Display", Font.PLAIN, 16));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    // ------------------- File Choosing -------------------
    private void chooseFile(ActionEvent e) {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            processFile(chooser.getSelectedFile());
        }
    }

    private void processFile(File file) {
        fileLabel.setText("📄 " + file.getName());
        animateStatsUpdate(file);
    }

    // ------------------- File Analysis -------------------
    private void animateStatsUpdate(File file) {
        try {
            java.util.List<String> lines = Files.readAllLines(Paths.get(file.getAbsolutePath()));
            long words = lines.stream().flatMap(l -> Arrays.stream(l.split("\\s+"))).filter(w -> !w.isEmpty()).count();
            long chars = lines.stream().mapToInt(String::length).sum();
            long lineCount = lines.size();
            long sizeKB = Files.size(Paths.get(file.getAbsolutePath())) / 1024;

            Timer timer = new Timer(30, null);
            int steps = 15;
            long wStart = Long.parseLong(wordsLabel.getText().replaceAll("\\D", ""));
            long cStart = Long.parseLong(charsLabel.getText().replaceAll("\\D", ""));
            long lStart = Long.parseLong(linesLabel.getText().replaceAll("\\D", ""));
            long sStart = Long.parseLong(sizeLabel.getText().replaceAll("\\D", ""));
            int[] step = {0};

            timer.addActionListener(ev -> {
                if (step[0] >= steps) {
                    wordsLabel.setText(words + "");
                    charsLabel.setText(chars + "");
                    linesLabel.setText(lineCount + "");
                    sizeLabel.setText(sizeKB + " KB");
                    ((Timer) ev.getSource()).stop();
                    return;
                }
                wordsLabel.setText(wStart + (words - wStart) * step[0] / steps + "");
                charsLabel.setText(cStart + (chars - cStart) * step[0] / steps + "");
                linesLabel.setText(lStart + (lineCount - lStart) * step[0] / steps + "");
                sizeLabel.setText(sStart + (sizeKB - sStart) * step[0] / steps + " KB");
                step[0]++;
            });
            timer.start();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error reading file: " + ex.getMessage());
        }
    }

    // ------------------- Card Hover Animation -------------------
    private void animateCardHover(JPanel card, boolean enter) {
        Timer timer = new Timer(15, null);
        int steps = 10;
        int[] count = {0};
        int shadowStart = enter ? 12 : 20;
        int shadowEnd = enter ? 20 : 12;

        timer.addActionListener(e -> {
            float ratio = (float) count[0] / steps;
            int val = (int) (shadowStart * (1 - ratio) + shadowEnd * ratio);
            card.putClientProperty(FlatClientProperties.STYLE,
                    "arc:20; background:#FFFFFFDD; borderWidth:0; shadowWidth:" + val + "; shadowOpacity:0.18;");
            card.repaint();
            count[0]++;
            if (count[0] > steps) ((Timer) e.getSource()).stop();
        });
        timer.start();
    }

    // ------------------- Dark / Light Theme -------------------
    private void toggleTheme(ActionEvent e) {
        try {
            if (darkMode) {
                FlatLightLaf.setup();
                themeButton.setText("☀ Light");
            } else {
                FlatDarkLaf.setup();
                themeButton.setText("🌙 Dark");
            }
            darkMode = !darkMode;
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // ------------------- Main -------------------
    public static void main(String[] args) {
        try {
            FlatLightLaf.setup();
        } catch (Exception ignored) {}
        SwingUtilities.invokeLater(() -> new LuxuryFileAnalyzer().setVisible(true));
    }
}