



import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.Timer;
import javax.swing.text.Position;


import acm.graphics.GLabel;
import acm.graphics.GOval;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;


public class MainClass extends GraphicsProgram implements ActionListener
{


    public GOval food; //recommnedation by video to use GOVal and GRecht


    private ArrayList<GRect> snake;

    private final int TILE_SIZE = 30;
    private final int GRID_WIDTH = 20;
    private final int GRID_HEIGHT = 30;
    private final int SCREEN_WIDTH = GRID_WIDTH * TILE_SIZE;
    private final int SCREEN_HEIGHT = GRID_HEIGHT * TILE_SIZE;


    private int snakeX, snakeY,
            snakeWidth= 15, snakeHeight =15; //leave the width and height in, easier plus square so unnited anyways
    private int direction = 0; //use numbers 1-4 to represent direction, makes for less confusion/typing


    public Timer timer = new Timer(200, this);


    private boolean isPlaying, isGameOver;
    private int score, previousScore;
    private GLabel scoreLabel;
    private GLabel opening;


    public void run()
    {
        addKeyListeners(); //keyboard inputs monitored
        addMouseListeners(); //monitor the mouse
        setBackground(Color.BLACK);
        setUpInfo();
        randomFood();
        drawSnake();
        timer.start();
        move();
    }
    private void setUpInfo () {
        opening = new GLabel("Welcome to classic snake, click to begin", getWidth()/3, getHeight()/2);
        opening.setColor(Color.WHITE);
        opening.setFont("Retro");
        add(opening);
        waitForClick();
        remove(opening);
        scoreLabel = new GLabel ("Your score is: " + score, 20,20);
        scoreLabel.setColor(Color.WHITE);
        scoreLabel.setFont("Retro");
        add(scoreLabel);
    }
    private void removeInstructions (MouseEvent e) {




    }
    private void randomFood() {
        Random rand = new Random();
        int x = rand.nextInt(getWidth() - 10); //calculate boundaries, don't spawn food outside
        int y = rand.nextInt(getHeight() - snakeHeight);
        food = new GOval(x, y, snakeWidth, snakeHeight); //food same mesurements as snake
        food.setFilled(true);
        food.setColor(Color.RED);
        add(food);
    }
    public void drawSnake()
    {
        snake = new ArrayList<>();
        for(int i=0;i<10;i++) {
            GRect bodyPart = new GRect(snakeX,snakeY,snakeWidth,snakeHeight);
            bodyPart.setColor(Color.GREEN);
            bodyPart.setFilled(true);
            snake.add(bodyPart);
            add(bodyPart);
        }
    }
    private boolean eatFood() {
        GRect head = snake.get(0);
        if (food.getBounds().intersects(head.getBounds()))
        {
            remove(food); // Remove the current food object
            randomFood(); // Generate a new food object
            growSnake(); // Grow
            return true;
        }
        else {
            return false;
        }
    }
   private boolean checkCollision () {
        if(head.x<0 || head.x >= GRID_WIDTH || head.y < 0 )
   }


    public void keyPressed(KeyEvent keyPressed)
    {
        int key = keyPressed.getKeyCode(); //for some reason the switch doesn't work
        if (key == KeyEvent.VK_UP && direction != 2)
        {
            direction = 1;
        } else if (key == KeyEvent.VK_DOWN && direction != 1)
        {
            direction = 2;
        } else if (key == KeyEvent.VK_LEFT && direction != 4)
        {
            direction = 3;
        } else if (key == KeyEvent.VK_RIGHT && direction != 3)
        {
            direction = 4;
        }
    }


    private void redrawSnake()
    {
        for (int i=snake.size()-1;i>0;i--)
        {
            snake.get(i).setLocation(snake.get(i - 1).getX(), snake.get(i - 1).getY());
            //get x and y respectively, transfer i > i-1, shifting things entirely on both height and width
        }
    }
    private void growSnake()
    {
        GRect head = snake.get(0);
        GRect bodyPart2 = new GRect(head.getX(),head.getY(),snakeWidth,snakeHeight);
        bodyPart2.setFillColor(Color.GREEN);
        bodyPart2.setFilled(true);
        snake.add(0,bodyPart2);
        if(checkCollision())
        {
            bodyPart2.setFillColor(Color.RED);
        }
        add(bodyPart2);
    }
    private void move()
    {
        redrawSnake();
        GRect head = snake.get(0); //obtain first value;
        int horz = (int) head.getX();
        int vert = (int) head.getY();
        if (direction==1) {
            vert -= snakeHeight;
            //System.out.println("move up");
        }
        else if (direction==2)
        {
            vert += snakeHeight;
            //System.out.println("move down");
        }
        else if(direction==3)
        {
            horz -= snakeWidth;
            //System.out.println("move right)");
        }
        else if (direction==4)
        {
            horz += snakeWidth;
            //System.out.println("move left");
        }
        head.setLocation (horz, vert);
    }
    @Override
    public void actionPerformed(ActionEvent arg0)
    {
        move();
        if (eatFood()) {
            remove(food);
            randomFood();
            score++;
            remove (scoreLabel);
            scoreLabel = new GLabel ("Your score is: " + score, 20,20);
            scoreLabel.setColor(Color.WHITE);
            scoreLabel.setFont("Retro");
            add(scoreLabel);
        }
        if (checkCollision())
        {
            System.out.println("mistake");
        }
    }
    public static void main(String[] args)
    {
        new MainClass().start();
    }
}




