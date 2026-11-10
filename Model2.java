package p;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Model2 extends JPanel implements ActionListener {

    private Dimension d;
    private final Font smallFont = new Font("Arial", Font.BOLD, 14);
    private Font largeFont = new Font("Arial", Font.BOLD, 30);

    private boolean inGame = false;
    private boolean dying = false;

    private static final int BLOCK_SIZE = 24;
    private static final int N_BLOCKS = 15;
    private static final int SCREEN_SIZE = N_BLOCKS * BLOCK_SIZE;
    private static final int maxGhosts = 12;
    private static final int pacmanSpeed = 6;

    private int N_GHOSTS = 6;
    private int lives;
    private int score;
    private int[] dx;
    private int[] dy;
    private int[] ghost_x;
    private int[] ghostY;
    private int[] ghostDx;
    private int[] ghostDy;
    private int[] ghostSpeed;

    private Image heart, ghost;
    private Image up, down, left, right;

    private int pacman_x;
    private int pacmanY;
    private int pacmanDx;
    private int pacmand_y;
    private int reqDx;
    private int reqDy;

    private int level = 1;
    private int levelBannerTimer;
    private boolean showLevelBanner;

    private final short[] levelData = {
            19, 18, 18, 26, 26, 18, 18, 18, 18, 18, 26, 26, 18, 18, 22,
            17, 16, 28, 00, 00, 25, 16, 16, 16, 28, 00, 00, 25, 16, 20,
            17, 20, 00, 19, 22, 00, 17, 16, 20, 00, 19, 22, 00, 17, 20,
            17, 16, 18, 24, 24, 18, 16, 16, 16, 18, 24, 24, 18, 16, 20,
            17, 16, 20, 00, 00, 17, 16, 16, 16, 20, 00, 00, 17, 16, 20,
            17, 16, 20, 00, 00, 17, 16, 16, 16, 20, 00, 00, 17, 16, 20,
            17, 16, 16, 18, 18, 16, 16, 24, 16, 16, 18, 18, 16, 16, 20,
            16, 16, 16, 24, 16, 16, 20, 00, 17, 16, 16, 24, 16, 16, 16,
            17, 16, 20, 00, 17, 16, 16, 18, 16, 16, 20, 00, 17, 16, 20,
            17, 16, 20, 00, 25, 16, 16, 16, 16, 16, 28, 00, 17, 16, 20,
            17, 16, 16, 22, 00, 25, 24, 24, 24, 28, 00, 19, 16, 16, 20,
            17, 16, 16, 16, 22, 00, 00, 00, 00, 00, 19, 16, 16, 16, 20,
            17, 16, 16, 16, 16, 18, 18, 18, 18, 18, 16, 16, 16, 16, 20,
            17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20,
            25, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 28
    };

// 0 = blue
// 1 = left border
// 2 = top border
// 4 = right border
// 8 = bottom border
// 16 = white dots

    private final short[] levelData2 = {
            19, 18, 18, 26, 26, 18, 18, 18, 18, 18, 26, 26, 18, 18, 22,
            17, 16, 28, 00, 00, 25, 16, 16, 16, 28, 00, 00, 25, 16, 20,
            17, 20, 00, 19, 22, 00, 17, 16, 20, 00, 19, 22, 00, 17, 20,
            17, 16, 18, 24, 24, 18, 16, 16, 16, 18, 24, 24, 18, 16, 20,
            17, 16, 20, 00, 00, 17, 16, 16, 16, 20, 00, 00, 17, 16, 20,
            17, 16, 20, 00, 00, 17, 16, 16, 16, 20, 00, 00, 17, 16, 20,
            17, 16, 16, 18, 18, 16, 16, 24, 16, 16, 18, 18, 16, 16, 20,
            16, 16, 16, 24, 16, 16, 20, 00, 17, 16, 16, 24, 16, 16, 16,
            17, 16, 20, 00, 17, 16, 16, 18, 16, 16, 20, 00, 17, 16, 20,
            17, 16, 20, 00, 25, 16, 16, 16, 16, 16, 28, 00, 17, 16, 20,
            17, 16, 16, 22, 00, 25, 24, 24, 24, 28, 00, 19, 16, 16, 20,
            17, 16, 16, 16, 22, 00, 00, 00, 00, 00, 19, 16, 16, 16, 20,
            17, 16, 16, 16, 16, 18, 18, 18, 18, 18, 16, 16, 16, 16, 20,
            17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20,
            25, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 28
    };

// 0 = blue
// 1 = left border
// 2 = top border
// 4 = right border
// 8 = bottom border
// 16 = white dots
// green = 18  red = 24  purple = 17  yellow = 20  cyan = 25  pink = 25  blue = 28  orange = 19  mint = 22  neon = 26

    private final short[] levelData3 = {
            19, 18, 18, 26, 26, 18, 18, 18, 18, 18, 26, 26, 18, 18, 22,
            17, 16, 28, 00, 00, 25, 16, 16, 16, 28, 00, 00, 25, 16, 20,
            17, 20, 00, 19, 22, 00, 17, 16, 20, 00, 19, 22, 00, 17, 20,
            17, 16, 18, 24, 24, 18, 16, 16, 16, 18, 24, 24, 18, 16, 20,
            17, 16, 20, 00, 00, 17, 16, 16, 16, 20, 00, 00, 17, 16, 20,
            17, 16, 20, 00, 00, 17, 16, 16, 16, 20, 00, 00, 17, 16, 20,
            17, 16, 16, 18, 18, 16, 16, 24, 16, 16, 18, 18, 16, 16, 20,
            16, 16, 16, 24, 16, 16, 20, 00, 17, 16, 16, 24, 16, 16, 16,
            17, 16, 20, 00, 17, 16, 16, 18, 16, 16, 20, 00, 17, 16, 20,
            17, 16, 20, 00, 25, 16, 16, 16, 16, 16, 28, 00, 17, 16, 20,
            17, 16, 16, 22, 00, 25, 24, 24, 24, 28, 00, 19, 16, 16, 20,
            17, 16, 16, 16, 22, 00, 00, 00, 00, 00, 19, 16, 16, 16, 20,
            17, 16, 16, 16, 16, 18, 18, 18, 18, 18, 16, 16, 16, 16, 20,
            17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20,
            25, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 28
    };

// green = 18  red = 24  purple = 17  yellow = 20  cyan = 25  pink = 25  blue = 28  orange = 19  mint = 22  neon = 26

    private final short[] levelData4 = {
            19, 18, 18, 26, 26, 18, 18, 18, 18, 18, 26, 26, 18, 18, 22,
            17, 16, 28, 00, 00, 25, 16, 16, 16, 28, 00, 00, 25, 16, 20,
            17, 20, 00, 19, 22, 00, 17, 16, 20, 00, 19, 22, 00, 17, 20,
            17, 16, 18, 24, 24, 18, 16, 16, 16, 18, 24, 24, 18, 16, 20,
            17, 16, 20, 00, 00, 17, 16, 16, 16, 20, 00, 00, 17, 16, 20,
            17, 16, 20, 00, 00, 17, 16, 16, 16, 20, 00, 00, 17, 16, 20,
            17, 16, 16, 18, 18, 16, 16, 24, 16, 16, 18, 18, 16, 16, 20,
            16, 16, 16, 24, 16, 16, 20, 00, 17, 16, 16, 24, 16, 16, 16,
            17, 16, 20, 00, 17, 16, 16, 18, 16, 16, 20, 00, 17, 16, 20,
            17, 16, 20, 00, 25, 16, 16, 16, 16, 16, 28, 00, 17, 16, 20,
            17, 16, 16, 22, 00, 25, 24, 24, 24, 28, 00, 19, 16, 16, 20,
            17, 16, 16, 16, 22, 00, 00, 00, 00, 00, 19, 16, 16, 16, 20,
            17, 16, 16, 16, 16, 18, 18, 18, 18, 18, 16, 16, 16, 16, 20,
            17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20,
            25, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 28
    };
// green = 18  red = 24  purple = 17  yellow = 20  cyan = 25  pink = 25  blue = 28  orange = 19  mint = 22  neon = 26
    private final short[] levelData5 = {
        19, 18, 18, 26, 26, 18, 18, 18, 18, 18, 26, 26, 18, 18, 22,
        17, 16, 28, 00, 00, 25, 16, 16, 16, 28, 00, 00, 25, 16, 20,
        17, 20, 00, 19, 22, 00, 17, 16, 20, 00, 19, 22, 00, 17, 20,
        17, 16, 18, 24, 24, 18, 16, 16, 16, 18, 24, 24, 18, 16, 20,
        17, 16, 20, 00, 00, 17, 16, 16, 16, 20, 00, 00, 17, 16, 20,
        17, 16, 20, 00, 00, 17, 16, 16, 16, 20, 00, 00, 17, 16, 20,
        17, 16, 16, 18, 18, 16, 16, 24, 16, 16, 18, 18, 16, 16, 20,
        16, 16, 16, 24, 16, 16, 20, 00, 17, 16, 16, 24, 16, 16, 16,
        17, 16, 20, 00, 17, 16, 16, 18, 16, 16, 20, 00, 17, 16, 20,
        17, 16, 20, 00, 25, 16, 16, 16, 16, 16, 28, 00, 17, 16, 20,
        17, 16, 16, 22, 00, 25, 24, 24, 24, 28, 00, 19, 16, 16, 20,
        17, 16, 16, 16, 22, 00, 00, 00, 00, 00, 19, 16, 16, 16, 20,
        17, 16, 16, 16, 16, 18, 18, 18, 18, 18, 16, 16, 16, 16, 20,
        17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20,
        25, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 28
    };

    private final int[] validSpeeds = {3, 4, 6, 8, 12};
    private short[] screenData;
    private Timer timer;

    public Model2() {
        loadImages();
        initVariables();
        addKeyListener(new TAdapter());
        setFocusable(true);
        initGame();
    }

    private void loadImages() {
        down = createDirectionalImage(Color.YELLOW, Color.ORANGE, 20, 20, 90);
        up = createDirectionalImage(Color.YELLOW, Color.ORANGE, 20, 20, -90);
        left = createDirectionalImage(Color.YELLOW, Color.ORANGE, 20, 20, 180);
        right = createDirectionalImage(Color.YELLOW, Color.ORANGE, 20, 20, 0);
        ghost = createGhostImage();
        heart = createHeartImage();
    }

    private BufferedImage createDirectionalImage(Color body, Color mouth, int width, int height, int rotation) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();
        g2.rotate(Math.toRadians(rotation), width / 2.0, height / 2.0);
        g2.setColor(body);
        g2.fillArc(0, 0, width, height, 30, 300);
        g2.setColor(mouth);
        g2.fillArc(0, 0, width, height, 30, 90);
        g2.dispose();
        return image;
    }

    private BufferedImage createGhostImage() {
        BufferedImage image = new BufferedImage(20, 20, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();
        g2.setColor(new Color(255, 100, 100));
        g2.fillRoundRect(2, 2, 16, 14, 8, 8);
        g2.fillRect(2, 10, 16, 6);
        g2.setColor(Color.WHITE);
        g2.fillOval(5, 5, 4, 4);
        g2.fillOval(11, 5, 4, 4);
        g2.dispose();
        return image;
    }

    private BufferedImage createHeartImage() {
        BufferedImage image = new BufferedImage(20, 20, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();
        g2.setColor(Color.RED);
        g2.fillOval(2, 2, 8, 8);
        g2.fillOval(10, 2, 8, 8);
        int[] xPoints = {2, 10, 18, 10, 2};
        int[] yPoints = {6, 2, 6, 18, 18};
        g2.fillPolygon(xPoints, yPoints, 5);
        g2.dispose();
        return image;
    }

    private void initVariables() {
        screenData = new short[N_BLOCKS * N_BLOCKS];
        d = new Dimension(400, 400);
        ghost_x = new int[maxGhosts];
        ghostDx = new int[maxGhosts];
        ghostY = new int[maxGhosts];
        ghostDy = new int[maxGhosts];
        ghostSpeed = new int[maxGhosts];
        dx = new int[4];
        dy = new int[4];

        timer = new Timer(40, this);
        timer.start();
    }

    private void playGame(Graphics2D g2d) {
        if (dying) {
            death();

        } else {
            movePacman();
            drawPacman(g2d);
            moveGhosts(g2d);
            checkMaze();
        }

        if(pacman_x > 360)
            pacman_x = 0;
        if(pacmanY > 360 - BLOCK_SIZE)
            pacmanY = 0;
        if(pacman_x < 0)
            pacman_x = 360 - BLOCK_SIZE;
        if(pacmanY < 0)
            pacmanY = 360 - BLOCK_SIZE;
    }

    private void showIntroScreen(Graphics2D g2d) {
        String start = "Press SPACE to start game";
        g2d.setColor(Color.pink);
        g2d.drawString(start, (SCREEN_SIZE)/4, 150);
    }

    private void drawScore(Graphics2D g) {
        g.setFont(smallFont);

        g.setColor(Color.PINK);
        String levelText = "Level: " + level;
        g.drawString(levelText, 12, 16);

        g.setColor(Color.PINK);
        String scoreText = "Score: " + score;
        g.drawString(scoreText, SCREEN_SIZE / 2 + 70, 16);

        for (int i = 0; i < lives; i++) {
            g.drawImage(heart, i * 28 + 8, SCREEN_SIZE + 1, this);
        }
    }

    private void startLevelBanner() {
        showLevelBanner = true;
        levelBannerTimer = 1000;
    }

    private void drawLevelBanner(Graphics2D g2d) {
        g2d.setFont(new Font("Arial", Font.BOLD, 24));
        g2d.setColor(new Color(255, 255, 255, 220));
        String bannerText = "LEVEL " + level;
        int textWidth = g2d.getFontMetrics().stringWidth(bannerText);
        int x = (SCREEN_SIZE - textWidth) / 2;
        int y = SCREEN_SIZE / 2;
        g2d.drawString(bannerText, x, y);
    }

    private void checkMaze() {
        int i = 0;
        boolean finished = true;

        while (i < N_BLOCKS * N_BLOCKS && finished) {
            if ((screenData[i] & 48) != 0) {
                finished = false;
            }
            i++;
        }
        if (finished) {
            score += 300 + level * 50;
            level++;

            startLevelBanner();

            if (N_GHOSTS < maxGhosts) {
                N_GHOSTS++;
            }

            if(level > 5)
                level = 1;

            initLevel(level);



        }
    }

    private void death() {
        lives--;
        if (lives == 0) {
            inGame = false;
            dying = false;
        } else {
            continueLevel();
        }
    }

    private void moveGhosts(Graphics2D g2d) {
        for (int i = 0; i < N_GHOSTS; i++) {
            moveGhost(g2d, i);
        }
    }

    private void moveGhost(Graphics2D g2d, int ghostIndex) {
        if (ghost_x[ghostIndex] % BLOCK_SIZE == 0 && ghostY[ghostIndex] % BLOCK_SIZE == 0) {
            chooseGhostDirection(ghostIndex);
        }

        int newX = ghost_x[ghostIndex] + ghostDx[ghostIndex] * ghostSpeed[ghostIndex];
        int newY = ghostY[ghostIndex] + ghostDy[ghostIndex] * ghostSpeed[ghostIndex];

        if (newX < 0) {
            newX = 0;
            ghostDx[ghostIndex] = 0;
            ghostDy[ghostIndex] = 1;
        } else if (newX > SCREEN_SIZE - BLOCK_SIZE) {
            newX = SCREEN_SIZE - BLOCK_SIZE;
            ghostDx[ghostIndex] = 0;
            ghostDy[ghostIndex] = -1;
        }

        if (newY < 0) {
            newY = 0;
            ghostDy[ghostIndex] = 0;
            ghostDx[ghostIndex] = 1;
        } else if (newY > SCREEN_SIZE - BLOCK_SIZE) {
            newY = SCREEN_SIZE - BLOCK_SIZE;
            ghostDy[ghostIndex] = 0;
            ghostDx[ghostIndex] = -1;
        }

        ghost_x[ghostIndex] = newX;
        ghostY[ghostIndex] = newY;
        drawGhost(g2d, ghost_x[ghostIndex] + 1, ghostY[ghostIndex] + 1);

        if (isGhostCollidingWithPacman(ghostIndex)) {
            dying = true;
        }
    }

    private void chooseGhostDirection(int ghostIndex) {
        int pos = ghost_x[ghostIndex] / BLOCK_SIZE
            + N_BLOCKS * (ghostY[ghostIndex] / BLOCK_SIZE);
        int count = 0;
        count = addGhostDirection(count, ghostIndex, pos, 1, -1, 0);
        count = addGhostDirection(count, ghostIndex, pos, 2, 0, -1);
        count = addGhostDirection(count, ghostIndex, pos, 4, 1, 0);
        count = addGhostDirection(count, ghostIndex, pos, 8, 0, 1);

        if (ghost_x[ghostIndex] > 360 - BLOCK_SIZE) {
            ghost_x[ghostIndex] = 0;
        }
        if (ghost_x[ghostIndex] < 0) {
            ghost_x[ghostIndex] = 360 - BLOCK_SIZE;
        }

        if (count == 0) {
            reverseOrStopGhost(ghostIndex, pos);
        } else {
            int direction = (int) (Math.random() * count);
            if (direction > 3) {
                direction = 3;
            }
            ghostDx[ghostIndex] = dx[direction];
            ghostDy[ghostIndex] = dy[direction];
        }
    }

    private int addGhostDirection(int count, int ghostIndex, int pos, int wallMask,
                                  int directionX, int directionY) {
        boolean canContinue = directionX != 0
                ? ghostDx[ghostIndex] != -directionX
                : ghostDy[ghostIndex] != -directionY;
        if ((screenData[pos] & wallMask) == 0 && canContinue) {
            dx[count] = directionX;
            dy[count] = directionY;
            return count + 1;
        }
        return count;
    }

    private void reverseOrStopGhost(int ghostIndex, int pos) {
        if ((screenData[pos] & 15) == 15) {
            ghostDx[ghostIndex] = 0;
            ghostDy[ghostIndex] = 0;
        } else {
            ghostDx[ghostIndex] = -ghostDx[ghostIndex];
            ghostDy[ghostIndex] = -ghostDy[ghostIndex];
        }
    }

    private boolean isGhostCollidingWithPacman(int ghostIndex) {
        return pacman_x > ghost_x[ghostIndex] - 12 && pacman_x < ghost_x[ghostIndex] + 12
                && pacmanY > ghostY[ghostIndex] - 12 && pacmanY < ghostY[ghostIndex] + 12
                && inGame;
    }

    private void drawGhost(Graphics2D g2d, int x, int y) {
        g2d.drawImage(ghost, x, y, this);
    }

    private void movePacman() {
        int pos;
        short ch;

        if (pacman_x % BLOCK_SIZE == 0 && pacmanY % BLOCK_SIZE == 0) {
            pos = pacman_x / BLOCK_SIZE + N_BLOCKS * (int) (pacmanY / BLOCK_SIZE);
            ch = screenData[pos];

            if ((ch & 16) != 0) {
                screenData[pos] = (short) (ch & 15);
                score += level * N_GHOSTS;
            }

            if (reqDx != 0 || reqDy != 0) {
                if (!((reqDx == -1 && reqDy == 0 && (ch & 1) != 0)
                || (reqDx == 1 && reqDy == 0 && (ch & 4) != 0)
                || (reqDx == 0 && reqDy == -1 && (ch & 2) != 0)
                || (reqDx == 0 && reqDy == 1 && (ch & 8) != 0))) {
                    pacmanDx = reqDx;
                    pacmand_y = reqDy;
                }
            }

            //check for standstill
            if ((pacmanDx == -1 && pacmand_y == 0 && (ch & 1) != 0)
                    || (pacmanDx == 1 && pacmand_y == 0 && (ch & 4) != 0)
                    || (pacmanDx == 0 && pacmand_y == -1 && (ch & 2) != 0)
                    || (pacmanDx == 0 && pacmand_y == 1 && (ch & 8) != 0)) {
                pacmanDx = 0;
                pacmand_y = 0;
            }
        }
        pacman_x = pacman_x + pacmanSpeed * pacmanDx;
        pacmanY = pacmanY + pacmanSpeed * pacmand_y;
    }

    private void drawPacman(Graphics2D g2d) {
        int directionX = pacmanDx != 0 ? pacmanDx : reqDx;
        int directionY = pacmand_y != 0 ? pacmand_y : reqDy;

        if (directionX == -1) {
            g2d.drawImage(left, pacman_x + 1, pacmanY + 1, this);
        } else if (directionX == 1) {
            g2d.drawImage(right, pacman_x + 1, pacmanY + 1, this);
        } else if (directionY == -1) {
            g2d.drawImage(up, pacman_x + 1, pacmanY + 1, this);
        } else {
            g2d.drawImage(down, pacman_x + 1, pacmanY + 1, this);
        }
    }

    private void drawMaze(Graphics2D g2d, short[] lData) {
        short i = 0;
        int x;
        int y;

        for (y = 0; y < SCREEN_SIZE; y += BLOCK_SIZE) {
            for (x = 0; x < SCREEN_SIZE; x += BLOCK_SIZE) {
                g2d.setColor(new Color(0, 72, 251));
                g2d.setStroke(new BasicStroke(5));

                if ((lData[i] == 0)) {
                    g2d.fillRect(x, y, BLOCK_SIZE, BLOCK_SIZE);
                }

                if ((screenData[i] & 1) != 0) {
                    g2d.drawLine(x, y, x, y + BLOCK_SIZE - 1);
                }

                if ((screenData[i] & 2) != 0) {
                    g2d.drawLine(x, y, x + BLOCK_SIZE - 1, y);
                }

                if ((screenData[i] & 4) != 0) {
                        g2d.drawLine(x + BLOCK_SIZE - 1, y, x + BLOCK_SIZE - 1,
                            y + BLOCK_SIZE - 1);
                }

                if ((screenData[i] & 8) != 0) {
                        g2d.drawLine(x, y + BLOCK_SIZE - 1, x + BLOCK_SIZE - 1,
                            y + BLOCK_SIZE - 1);
                }

                if ((screenData[i] & 16) != 0) {
                    g2d.setColor(new Color(255,255,255));
                    g2d.fillOval(x + 10, y + 10, 6, 6);
                }
                i++;
            }
        }
    }

    private void initGame() {
        lives = 8;
        score = 0;
        N_GHOSTS = 1;
        level = 1;
        initLevel();
    }

    public void resetGame(Graphics2D g2d)
    {
        String gameOver = "GAME OVER";
        String finalScore = "Score = " + score;
        String playAgain = "press SPACE to play again";

        g2d.setColor(Color.red);
        g2d.setFont(largeFont);

        g2d.drawString(gameOver, (SCREEN_SIZE)/4, 125);
        g2d.drawString(finalScore, (SCREEN_SIZE)/4, 150);
        g2d.drawString(playAgain, (SCREEN_SIZE)/4, 175);
    }

    private void initLevel() {
        System.arraycopy(levelData, 0, screenData, 0, N_BLOCKS * N_BLOCKS);
        removeEveryNthPellet(2);
        startLevelBanner();
        continueLevel();
    }
    private void initLevel(int level) {
        if(level > 5)
            level = 1;

        N_GHOSTS = level;
        if(level == 1)
            initLevel();
        if(level == 2)
            initLevel2();
        if(level == 3)
            initLevel3();
        if(level == 4)
            initLevel4();
        if(level == 5)
            initLevel5();

        continueLevel();
    }

    private void initLevel2()
    {
        level = 2;
        System.arraycopy(levelData2, 0, screenData, 0, N_BLOCKS * N_BLOCKS);
        removeEveryNthPellet(5);
        continueLevel();
    }

    private void removeEveryNthPellet(int interval) {
        int pelletIndex = 0;
        for (int i = 0; i < screenData.length; i++) {
            if ((screenData[i] & 16) != 0) {
                if (pelletIndex % interval == 0) {
                    screenData[i] = (short) (screenData[i] & 15);
                }
                pelletIndex++;
            }
        }
    }

    private void initLevel3()
    {
        level = 3;
        System.arraycopy(levelData3, 0, screenData, 0, N_BLOCKS * N_BLOCKS);
        continueLevel();
    }
    private void initLevel4()
    {
        level = 4;
        System.arraycopy(levelData4, 0, screenData, 0, N_BLOCKS * N_BLOCKS);
        continueLevel();
    }
    private void initLevel5()
    {
        level = 5;
        System.arraycopy(levelData5, 0, screenData, 0, N_BLOCKS * N_BLOCKS);
        continueLevel();
    }

    private void nextLevel()
    {
        level++;
        initLevel(level);
    }

    private void continueLevel() {
        int ghostDirection = 1;
        int levelSpeed = validSpeeds[Math.min(level - 1, validSpeeds.length - 1)];

        for (int i = 0; i < N_GHOSTS; i++) {
            ghostY[i] = 4 * BLOCK_SIZE; // start position
            ghost_x[i] = 4 * BLOCK_SIZE;
            ghostDy[i] = 0;
            ghostDx[i] = ghostDirection;
            ghostDirection = -ghostDirection;
            ghostSpeed[i] = levelSpeed;
        }
        pacman_x = 7 * BLOCK_SIZE; // start position
        pacmanY = 11 * BLOCK_SIZE;
        pacmanDx = 0; // reset direction move
        pacmand_y = 0;
        reqDx = 0; // reset direction controls
        reqDy = 0;
        dying = false;
    }

    public void subtractGhost()
    {
        if(N_GHOSTS > 0)
            N_GHOSTS--;
    }
    public void addGhost()
    {
        if(N_GHOSTS < maxGhosts)
            N_GHOSTS++;

        continueLevel();
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, d.width, d.height);

        if(level > 5)
            level = 1;

        if(level == 1)
            drawMaze(g2d, levelData);
        if(level == 2)
            drawMaze(g2d, levelData2);
        if(level == 3)
            drawMaze(g2d, levelData3);
        if(level == 4)
            drawMaze(g2d, levelData4);
        if(level == 5)
            drawMaze(g2d, levelData5);

        if (showLevelBanner) {
            drawLevelBanner(g2d);
        }

        drawScore(g2d);
        if (inGame) {
            playGame(g2d);
        }
        else if(lives == 0)
            resetGame(g2d);

        else
        {
            showIntroScreen(g2d);
        }
        Toolkit.getDefaultToolkit().sync();
    }

    //controls
    class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            if (key == KeyEvent.VK_N)
            {
                nextLevel();
            }
            if (key == KeyEvent.VK_S)
            {
                subtractGhost();
            }
            if (key == KeyEvent.VK_A)
            {
                addGhost();
            }
            if (inGame) {
                if (key == KeyEvent.VK_LEFT) {
                    reqDx = -1;
                    reqDy = 0;
                } else if (key == KeyEvent.VK_RIGHT) {
                    reqDx = 1;
                    reqDy = 0;
                } else if (key == KeyEvent.VK_UP) {
                    reqDx = 0;
                    reqDy = -1;
                } else if (key == KeyEvent.VK_DOWN) {
                    reqDx = 0;
                    reqDy = 1;
                } else if (key == KeyEvent.VK_ESCAPE && timer.isRunning()) {
                    inGame = false;
                }
            } else {
                if (key == KeyEvent.VK_SPACE) {
                    inGame = true;
                    initGame();
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (showLevelBanner) {
            levelBannerTimer -= 40;
            if (levelBannerTimer <= 0) {
                showLevelBanner = false;
                levelBannerTimer = 0;
            }
        }
        repaint();
    }
}