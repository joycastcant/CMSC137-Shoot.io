import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import java.awt.AlphaComposite;

public class Game extends JPanel implements KeyListener {
  // MultiThreadChatClient client;
  Client client;
  //adjustments between game and the field 2D array
  final static int ROW_ADJUST = 8;
  final static int COL_ADJUST = 6;
  final static int TILE_SIZE = 50;

  //directions
  final static int NONE = 0;
  final static int UP = 1;
  final static int RIGHT = 2;
  final static int DOWN = 3;
  final static int LEFT = 4;
  final static int MAXHP = 100;

  private int camX;
  private int camY;
  private int height;
  private int width;
  private int offSet = 50;
  private int tileSize = 50;
  private Tile block = new Tile("images/prison_platform.png");
  private Tile floor = new Tile("images/tile.png");
  private int direction;
  private Chat chat;
  private boolean isInGame = true;
  private String message = "";
  private Thread clThread;
  private int flag = 1;
  private Player player;
  private String name;
  private String host;
  private String port;
  private Map map;
  private HashMap<Integer, Bomb> deadBombs = new HashMap<Integer, Bomb>();
  private HashMap<String, Player> players = new HashMap<String, Player>();
  protected ArrayList<Bomb> bombs = new ArrayList<Bomb>();
  private Font font;


  //original map
  /* private int [][]field = {     {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},   //initial map with no destroyable blocks
                        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                        {1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                        {1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1},
                        {1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                        {1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1},
                        {1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                        {1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1},
                        {1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                        {1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1},
                        {1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
                    }; */
    //extended map
    private int [][]field = {
                        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                        {1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1},
                        {1, 1, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                        {1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                        {1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1},
                        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                        {1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1},
                        {1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                        {1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1},
                        {1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                        {1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1},
                        {1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1},
                        {1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                        {1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1},
                        {1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                        {1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                        {1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1},
                        {1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                        {1, 1, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1},
                        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                        {1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1},
                        {1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1},
                        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
                    };
    final static int[][] FIELD = {
                        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                        {1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1},
                        {1, 1, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                        {1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                        {1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1},
                        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                        {1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1},
                        {1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                        {1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1},
                        {1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                        {1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1},
                        {1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1},
                        {1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                        {1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1},
                        {1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                        {1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                        {1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1},
                        {1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                        {1, 1, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1},
                        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                        {1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1},
                        {1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1},
                        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
                    };

  public Game(int width, int height, int camX, int camY, String host, String port, String name) {
    this.width = field.length * this.tileSize;//width;
    this.height = field[0].length * this.tileSize;//height;
    this.camX = camX;
    this.camY = camY;
    this.direction = NONE;
    this.registerFont();
    this.setBackground(Color.black);
    this.addKeyListener(this);

    this.host = host;
    this.port = port;
    this.name = name;

    try {
      this.client = new Client(this.host, this.port, this);
      this.player =  new Player(this.name, this.field, this.client.getId(), "images/player.png");
      Thread receiver = new Thread(this.client.getReceiver());
      Thread sender = new Thread(this.client.getSender());
      receiver.start();
      sender.start();
    } catch(Exception e) {
      System.err.println(e);
    }

    ArrayList<Object> data = new ArrayList<Object>();
    data.add(this.deadBombs);
    data.add(this.player);
    this.client.getSender().setData(data);
  }

  public void registerFont() {
    GraphicsEnvironment ge = null;
    try{
      ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
      ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, this.getClass().getResourceAsStream("resources/joystix.monospace.ttf")));
    } catch(FontFormatException e){} catch (IOException e){} catch(NullPointerException e){}
    this.font = new Font("Joystix Monospace", Font.PLAIN, 12);
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    int x,y;
    // sudoX and sudoY represents the x and y of visible map to the camera
    //i and j represents the x and y of the window
    this.camX = (player.getPosX() - ROW_ADJUST) * this.tileSize;
    this.camY = (player.getPosY() - COL_ADJUST) * this.tileSize;
    this.map.setPosition(this.player.getPosX(), this.player.getPosY());

    // FOR TILES AND BLOCKS
    for(int i=0, sudoX=this.camX; i<=this.width && sudoX<=this.width; i+=this.tileSize, sudoX+=this.tileSize) {
      for(int j=0, sudoY=this.camY; j<=this.height && sudoY<=this.height; j+=this.tileSize, sudoY+=this.tileSize) {
        x = sudoX/this.tileSize;
        y = sudoY/this.tileSize;

        // check if block or floor
        try {
          if(i == (ROW_ADJUST*this.tileSize) && j == (COL_ADJUST*this.tileSize)) {
             g.drawImage(this.floor.getTile(), i, j, null);
          } else if(field[x][y] == 1) // IF FIELD CONTAINS BLOCK
              g.drawImage(this.block.getTile(), i, j, this);
            else // IF FIELD CONTAINS NOTHING
              g.drawImage(this.floor.getTile(), i, j, this);
        } catch(ArrayIndexOutOfBoundsException e) { }
      }
    }

    // FOR PLAYERS AND BOMBS
    for(int i=0, sudoX=this.camX; i<=this.width && sudoX<=this.width; i+=this.tileSize, sudoX+=this.tileSize) {
      for(int j=0, sudoY=this.camY; j<=this.height && sudoY<=this.height; j+=this.tileSize, sudoY+=this.tileSize) {
        x = sudoX/this.tileSize;
        y = sudoY/this.tileSize;

        // check if block or floor
        try {
          if(i == (ROW_ADJUST*this.tileSize) && j == (COL_ADJUST*this.tileSize)) {
            /* this.player.getWeapon().setX(i);
            this.player.getWeapon().setY(j);
            this.updateBullets(this.player); */

             switch (this.direction) {
              case UP:
                g.drawImage(player.getSprite("images/playerRight.png"), i, j, 50, 50, null);
                break;
              case RIGHT:
                g.drawImage(player.getSprite("images/playerRight.png"), i, j, 50, 50, null);
                break;
              case DOWN:
                g.drawImage(player.getSprite("images/playerLeft.png"), i, j, 50, 50, null);
                break;
              case LEFT:
                g.drawImage(player.getSprite("images/playerLeft.png"), i, j, 50, 50, null);
                break;
              default:
                g.drawImage(player.getSprite("images/playerRight.png"), i, j, 50, 50, null);
                break;
            }
          } else if(field[x][y] == 6)   {
            /* Player p = this.getPlayerByPos(x, y);
            System.out.println(this.player.getName() + "'s WINDOW:\n" + "Name: " + p.getName());
            p.getWeapon().setX(i);
            p.getWeapon().setY(j);
            this.updateBullets(p); */
            g.drawImage(player.getSprite("images/playerRight.png"), i, j, 50, 50, null);
          } else if(field[x][y] == 5) {
            /* Player p = this.getPlayerByPos(x, y);
            System.out.println(this.player.getName() + "'s WINDOW:\n" + "Name: " + p.getName());
            p.getWeapon().setX(i);
            p.getWeapon().setY(j);
            this.updateBullets(p); */
            g.drawImage(player.getSprite("images/playerLeft.png"), i, j, 50, 50, null);
          }

          else { // IF FIELD CONTAINS -NO- BLOCK
            if(field[x][y] == 2) { // IF FIELD CONTAINS BOMB
              // Check each bomb position
              for(int k=0;k<this.bombs.size();k++) {
                Bomb currBomb = this.bombs.get(k);
                if(currBomb == null) break;

                if(currBomb.isDead()) { // remove bomb if exploded
                  this.field[currBomb.getX()][currBomb.getY()] = 0;
                  this.bombs.remove(k);
                } else if(currBomb.getX() == x && currBomb.getY() == y) {
                  g.drawImage(currBomb.getImg().getTile(), i, j, this);

                  //------ Check explosion of bomb
                  if(!currBomb.isExploding()) g.drawImage(currBomb.getImg().getTile(), i, j, this);
                  else {
                    g.drawImage(currBomb.getSprite("images/ex1.png"), i, j, this);

                    //---- SOUTH
                    for(int l=1;l<currBomb.getExplosion()+1;l++) {
                        if ( y+l < this.field[0].length && this.field[x][(y+l)] != 1 ) {
                          if ( l == currBomb.getExplosion() || (y+(l+1) > this.field[0].length && this.field[x][(y+(l+1))] == 1 && l<currBomb.getExplosion()+1))
                            g.drawImage(currBomb.getSprite("images/ex4.png"), i, (j+(l*this.tileSize)), this);
                          else if( y+(l+1) < this.field[0].length && this.field[x][(y+(l+1))] == 1)
                            g.drawImage(currBomb.getSprite("images/ex6.png"), i, (j+(l*this.tileSize)), this);
                          else
                            g.drawImage(currBomb.getSprite("images/ex6.png"), i, (j+(l*this.tileSize)), this);
                        } else break;
                    }

                    //---- NORTH
                    for(int l=1;l<currBomb.getExplosion()+1;l++) {
                        if ( y-l > -1 && this.field[x][(y-l)] != 1 ) {
                          if ( l == currBomb.getExplosion() || (y-(l+1) > -1 && this.field[x][(y-(l+1))] == 1 && l<currBomb.getExplosion()+1))
                            g.drawImage(currBomb.getSprite("images/ex2.png"), i, (j-(l*this.tileSize)), this);
                          else if( y-(l+1) < this.field[0].length && this.field[x][(y-(l+1))] == 1)
                            g.drawImage(currBomb.getSprite("images/ex6.png"), i, (j-(l*this.tileSize)), this);
                          else
                            g.drawImage(currBomb.getSprite("images/ex6.png"), i, (j-(l*this.tileSize)), this);
                        } else break;
                    }

                    //---- EAST
                    for(int l=1;l<currBomb.getExplosion()+1;l++) {
                        if ( x+l < this.field.length && this.field[(x+l)][y] != 1 ) {
                          if ( l == currBomb.getExplosion() || (x+(l+1) > this.field.length && this.field[x+(l+1)][(y)] == 1 && l<currBomb.getExplosion()+1))
                            g.drawImage(currBomb.getSprite("images/ex3.png"), i+(l*this.tileSize), (j), this);
                          else if( x+(l+1) < this.field[0].length && this.field[x+(l+1)][(y)] == 1)
                            g.drawImage(currBomb.getSprite("images/ex7.png"), i+(l*this.tileSize), (j), this);
                          else
                            g.drawImage(currBomb.getSprite("images/ex7.png"), (i+(l*this.tileSize)), j, this);
                        } else break;
                    }

                    //---- WEST
                    for(int l=1;l<currBomb.getExplosion()+1;l++) {
                        if ( x-l > -1 && this.field[(x-l)][y] != 1 ) {
                          if ( l == currBomb.getExplosion() || (x-(l+1) > -1 && this.field[x-(l+1)][(y)] == 1 && l<currBomb.getExplosion()+1))
                            g.drawImage(currBomb.getSprite("images/ex5.png"), i-(l*this.tileSize), (j), this);
                          else if( x-(l+1) < this.field[0].length && this.field[x-(l+1)][(y)] == 1)
                            g.drawImage(currBomb.getSprite("images/ex7.png"), i-(l*this.tileSize), (j), this);
                          else
                            g.drawImage(currBomb.getSprite("images/ex7.png"), (i-(l*this.tileSize)), j, this);
                        } else break;
                    }
                  }

                }
              }
            }
            // for (Player p : players.values()) {
            //   if(x == p.getPosX() && y == p.getPosX())
            //     g.drawString(p.getName() ,i,j);
            // }
          }
        } catch(ArrayIndexOutOfBoundsException e) { }
        if(i == (ROW_ADJUST*this.tileSize) && j == (COL_ADJUST*this.tileSize)) g.drawString(player.getName() ,i,j);
      }
    }

    //---- STATS
    Graphics2D g2d = (Graphics2D)g;
    g2d.setColor(Color.BLACK);

    Composite originalComposite = g2d.getComposite();
    AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f);

    g2d.setComposite(alphaComposite);
    g2d.setColor(Color.BLACK);
    g2d.fillRect(0,575 ,800,30);
    g2d.setComposite(originalComposite);

    g.setFont(font);
    g.setColor(Color.WHITE);
    g.drawString("Health:   " + player.getHP() ,300,590);
    g.drawString("Score:   " + player.getPoints() ,480,590);
    g.drawString("Kills:   " + player.getKills() ,660,590);

    //---- BULLETS
    /* ArrayList<Bullet> bulletArray = this.player.getWeapon().getBullets();
		for(int j = 0; j < bulletArray.size(); j++) {
      Bullet bullet = bulletArray.get(j);
      g2d.setColor(bullet.getColor());
			g2d.fillRect(bullet.getX(), bullet.getY(), bullet.getWidth(), bullet.getHeight());
    } */

    this.drawBullets(g2d);
    
    setFocusable(true);
    if (this.isInGame) {
      requestFocus();
    }
    // this.updateBullets();
    this.repaint();
    this.revalidate();
  }

  public void drawBullets(Graphics2D g2d) {
    ArrayList<Player> pList = new ArrayList<Player>(this.players.values());
    for(int i = 0; i < pList.size(); i++) {
      Player p = pList.get(i);

      ArrayList<Bullet> bulletArray = p.getWeapon().getBullets();
      for(int j = 0; j < bulletArray.size(); j++) {
        Bullet bullet = bulletArray.get(j);
        // System.out.println("bullet-x: " + bullet.getX() + "\nbullet-y: " + bullet.getY());
        g2d.setColor(bullet.getColor());
        g2d.fillRect(bullet.getX(), bullet.getY(), bullet.getWidth(), bullet.getHeight());
      }
    }
  }

  /*==============
    KEYLISTENERS
  ===============*/

  @Override
  public void keyPressed( KeyEvent e ){

    if( e.getKeyCode() == KeyEvent.VK_ENTER) {
      if(isInGame) {
        isInGame = false;
        if(flag == 1){
          this.chat.clThread.start();
          this.flag = 0;
        }
      }  else {
        isInGame = true;
      }
    }

    if( e.getKeyCode() == KeyEvent.VK_L) {  //show leaderboard. alter key
      ArrayList<Player> pList = new ArrayList<>(this.players.values());
      for(int i = 0; i < pList.size(); i++) {
        System.out.println(pList.get(i).getName()); //prints the name of ith player
      }
    }

    // bomb
    // if( e.getKeyCode() == KeyEvent.VK_UP) {
    //   if(this.bombs.size()!=0) {
    //     this.bombs.get(0).explode();
    //   }
    // }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    //this.direction = NONE;
    int nextX = 0;
    int nextY = 0;

    ArrayList<Object> data = new ArrayList<Object>();
    data.add(this.deadBombs);
    data.add(player);

    if( e.getKeyCode() == KeyEvent.VK_S) {
      nextX = (this.camX/this.tileSize) + ROW_ADJUST;
      nextY = (this.camY + this.offSet)/this.tileSize + COL_ADJUST;
      this.direction = DOWN;
      player.setDirection(DOWN);
      if(field[nextX][nextY] != 1) {
        this.camY += this.offSet;
        this.client.getSender().setData(data);
      }
      player.moveDown(field);
    }
    if( e.getKeyCode() == KeyEvent.VK_W) {
      nextX = (this.camX/this.tileSize) + ROW_ADJUST;
      nextY = (this.camY - this.offSet)/this.tileSize + COL_ADJUST;
      this.direction = UP;
      player.setDirection(UP);
      if(field[nextX][nextY] != 1) {
        this.camY -= this.offSet;
        this.client.getSender().setData(data);
      }
      player.moveUp(field);
    }
    if( e.getKeyCode() == KeyEvent.VK_A) {
      nextX = (this.camX - this.offSet)/this.tileSize + ROW_ADJUST;
      nextY = (this.camY/this.tileSize) + COL_ADJUST;
      this.direction = LEFT;
      player.setDirection(LEFT);
      if(field[nextX][nextY] != 1) {
        this.camX -= this.offSet;
        this.client.getSender().setData(data);
      }
      player.moveLeft(field);
    }
    if( e.getKeyCode() == KeyEvent.VK_D) {
      nextX = (this.camX + this.offSet)/this.tileSize + ROW_ADJUST;
      nextY = (this.camY/this.tileSize) + COL_ADJUST;
      this.direction = RIGHT;
      player.setDirection(RIGHT);
      if(field[nextX][nextY] != 1) {
        this.camX += this.offSet;
        this.client.getSender().setData(data);
      }
      player.moveRight(field);
    }

    if( e.getKeyCode() == KeyEvent.VK_Q) {
      this.player.switchWeapon();
    }

    if( e.getKeyCode() == KeyEvent.VK_E) {
      this.player.switchWeapon();
    }

    if( e.getKeyCode() == KeyEvent.VK_SPACE) {
      System.out.println("SHOOT");
      this.player.shoot();
    }

    if( e.getKeyCode() == KeyEvent.VK_UP) {
      this.player.setPoints(Player.KILL);
      /* if(this.bombs.size()!=0) {
        this.bombs.get(0).explode();
        this.deadBombs.put(0, this.bombs.get(0));
      } */
    }

    if( e.getKeyCode() == KeyEvent.VK_ESCAPE) {
      Object[] options = new Object[2];
      options[0] = "YES";
      options[1] = "NO";

      JOptionPane escPane = new JOptionPane("Are you sure you want to exit?", JOptionPane.WARNING_MESSAGE, JOptionPane.DEFAULT_OPTION, null, options);

      JDialog dialog = escPane.createDialog(null, "ShooterIO: Goodbye?");

      dialog.setVisible(true);

      if((escPane.getValue()).equals(options[0])) {
          System.exit(0);
      }
    }
  }

  @Override
  public void keyTyped(KeyEvent arg0) {
  }

  public void setInGameStatus(boolean isInGame) {
    System.out.println("Here");
    this.isInGame = isInGame;
    requestFocus();
  }

  public void setChat(Chat chat) {
    this.chat = chat;
  }

  public boolean getIsFocused() {
    return this.isInGame;
  }

  public String getMessage () {
    return this.message;
  }

  public void setMessage(String m) {
    this.message = m;
  }

  public void appendMsg(String msg) {
    this.chat.appendMessage(msg);
  }

  public void setMap(Map map) {
    this.map = map;
  }

  public int[][] getField() {
    return this.field;
  }

  public void setBombs(HashMap<Integer, Bomb> bms, boolean initial) {
    if(initial == true) {
      this.bombs = new ArrayList<Bomb>();
      for(int i = 0; i < bms.size(); i++) {
        Bomb b = bms.get(i);
        int x = b.getX();
        int y = b.getY();
        Bomb sample = new Bomb(0, 0);
        b.setTile(sample.getImg());
        this.bombs.add(b);
        this.field[x][y] = 2;
      }
    } else if(initial == false) {
      for(int i = 0; i < this.bombs.size(); i++) {
        if(bms.containsKey(i)) {
          Bomb b = this.bombs.get(i);
          int x = b.getX();
          int y = b.getY();
          b.explode();
          this.deadBombs.remove(i);
        }
      }
    }
  }

  public void addPlayer(Player p) {
    this.players.put(p.getId(), p); //player is always unique
  }

  private void updateBullets(Player p){
		ArrayList<Bullet> bulletArray = p.getWeapon().getBullets();

		for(int i = 0; i < bulletArray.size(); i++){
			bulletArray.get(i).move();
    }
	}

  private Player getPlayerByPos(int x, int y) {
    ArrayList<Player> pList = new ArrayList<Player>(this.players.values());
    for(int i = 0; i < pList.size(); i++) {
      Player p = pList.get(i);

      if(p.getPosX() == x && p.getPosY() == y) return p;
    }

    return this.player;
  }

  private void updatePlayers() {  
    ArrayList<Player> pList = new ArrayList<Player>();  
    for(int i = 0; i < pList.size(); i++) {
      Player pl = pList.get(i);
      System.out.println(this.player.getName() + "'s WINDOW:\n" + "Name: " + pl.getName());
      System.out.println("PUCHA" + (ROW_ADJUST + (pl.getPosX() - this.player.getPosX())) * 50);
      pl.getWeapon().setX((ROW_ADJUST + (pl.getPosX() - this.player.getPosX())) * 50);
      pl.getWeapon().setY((COL_ADJUST + (pl.getPosY() - this.player.getPosY())) * 50);
    }
  }

  public Player getPlayer() {
    return this.player;
  }
}
