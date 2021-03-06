import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.applet.*;
import javax.sound.sampled.*;
import java.io.*;

public class Game extends JPanel {
	//NetBeans components
	private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private ShipStatusIcon jLabel10;
    private ShipStatusIcon jLabel11;
    private ShipStatusIcon jLabel12;
    private ShipStatusIcon jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private ShipStatusIcon jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private ShipStatusIcon jLabel5;
    private javax.swing.JLabel jLabel6;
    private ShipStatusIcon jLabel7;
    private ShipStatusIcon jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private GridPanel jPanel2;
    private GridPanel jPanel4;
	
	//general constants
	final int WIDTH = 420; //Enemy / user board width
	final String ERROR = "err";
	
	//Image Icons
	ImageIcon greenIcon; 
	ImageIcon blueIcon;
	ImageIcon redIcon;
	ImageIcon blackShips;
	ImageIcon whiteShips;
	ImageIcon whiteCommander;
	ImageIcon blackCommander;
	
	//Game states
	private boolean settingShips;
	private boolean attack; 
	private boolean defend;
	
	//JComponents
	private Client client;
	private JLabel commander;
	private JPanel gamePanel; //Top level panel that is added to this
	private GridPanel userBoard;
	private GridPanel enemyBoard;
	private JButton submit;
	private JButton rotate;
	private ShipStatusIcon[] userShipStatusIcons;
	private ShipStatusIcon[] enemyShipStatusIcons;
	private JLabel username;
	private JLabel enemy;
	private JButton home;
	private JButton help;
	private JButton settings;
	private JLabel ships1;
	private JLabel ships2;
	private JLabel commanderImage;
	private JLabel battleshipTitle;
	private File userHitFile;
	private File userMissFile;
	private File enemyHitFile;
	private File enemyMissFile;
	private FloatControl userHitControl;
	private FloatControl userMissControl;
	private FloatControl enemyHitControl;
	private FloatControl enemyMissControl;
	private Clip userHitClip;
	private Clip userMissClip;
	private Clip enemyHitClip;
	private Clip enemyMissClip;
	
	final boolean DEBUG = false;
	
	/** Constructor for the game JPanel */
    public Game(Client client){
		this.client = client;
		
		try{
			greenIcon = new ImageIcon(  getClass().getResource("images/green_circle.png")  );
			blueIcon = new ImageIcon(  getClass().getResource("images/blue_circle.png")  );
			redIcon = new ImageIcon(  getClass().getResource("images/red_circle.png")  );
			blackShips = new ImageIcon(  getClass().getResource("images/black_ships.png")  );
			whiteShips = new ImageIcon(  getClass().getResource("images/white_ships.png")  );
			blackCommander = new ImageIcon(  getClass().getResource("images/black_commander.png")  );
			whiteCommander = new ImageIcon(  getClass().getResource("images/white_commander.png")  );
		}catch(Exception e){
			System.out.println("Image folder not found!");
		}
		
		this.initComponents(); //initializes the components (generated by NETBEANS)
		
		//better names
		gamePanel = jPanel1;
		userBoard = jPanel2;
		enemyBoard = jPanel4;
		commander = jLabel4;
		submit = jButton4;
		rotate = jButton6;
		username = jLabel16;
		home = jButton5;
		settings = jButton3;
		help = jButton2;
		enemy = jLabel15;
		ships1 = jLabel6;
		ships2 = jLabel9;
		commanderImage = jLabel3;
		battleshipTitle = jLabel14;
	
		//Setting initial game states
		settingShips = true;
		attack = false; 
		defend = false;
		
		//Inits grids / ships
		userBoard.initShips();
		userBoard.initGrid();
		enemyBoard.initGrid();
		
		//Initializing green dots
		this.initIcons();
		
		//Sets boards unclickable
		userBoard.setUnclickable();
		enemyBoard.setUnclickable();
		
		commander.setText("<html>Set your <br>ships<br>commander!</html>");
		
		this.add(gamePanel);//Adding game panel to this panel
    }
	
	public boolean isSettingShips(){
		return settingShips;
	}
	
	public boolean isAttack(){
		return attack;
	}
	
	public void setName(String name){
		jLabel16.setText(name);
	}
	
	public void initFX(){
		try{
			userHitFile = new File("music/UserHit.wav");
			userMissFile = new File("music/UserMiss.wav");
			enemyHitFile = new File("music/EnemyHit.wav");
			enemyMissFile = new File("music/EnemyMiss.wav");
		}catch(NullPointerException e){
			System.out.println(e.getMessage());
			return;
		}
		try{
			//UserHit
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(userHitFile);
			userHitClip = AudioSystem.getClip();
			userHitClip.open(audioInputStream);
			userHitControl = (FloatControl) userHitClip.getControl(FloatControl.Type.MASTER_GAIN);
			//UserMiss
			audioInputStream = AudioSystem.getAudioInputStream(userMissFile);
			userMissClip = AudioSystem.getClip();
			userMissClip.open(audioInputStream);
			userMissControl = (FloatControl) userMissClip.getControl(FloatControl.Type.MASTER_GAIN);
			//EnemyHit
			audioInputStream = AudioSystem.getAudioInputStream(enemyHitFile);
			enemyHitClip = AudioSystem.getClip();
			enemyHitClip.open(audioInputStream);
			enemyHitControl = (FloatControl) enemyHitClip.getControl(FloatControl.Type.MASTER_GAIN);
			//EnemyMiss
			audioInputStream = AudioSystem.getAudioInputStream(enemyMissFile);
			enemyMissClip = AudioSystem.getClip();
			enemyMissClip.open(audioInputStream);
			enemyMissControl = (FloatControl) enemyMissClip.getControl(FloatControl.Type.MASTER_GAIN);
		}catch(UnsupportedAudioFileException e){
			System.out.println(e.getMessage());
		}catch(LineUnavailableException e){
			System.out.println(e.getMessage());
		}catch(IOException e){
			System.out.println(e.getMessage());
		}
	}
	
	public void setFXLevel(int value){
		try{
			double gain = value/100.0;
			float dB = (float) (Math.log(gain) / Math.log(10.0) * 20.0);
			userHitControl.setValue(dB);
			userMissControl.setValue(dB);
			enemyHitControl.setValue(dB);
			enemyMissControl.setValue(dB);
		}catch(NullPointerException e){
			System.out.println("Music folder not initialized!");
		}
	}
	
	public void setColorBlind(){
		for(ShipStatusIcon icon : userShipStatusIcons){
			if(!icon.isSunk()){
				icon.setIcon(blueIcon);
			}
		}
		for(ShipStatusIcon icon : enemyShipStatusIcons){
			if(!icon.isSunk()){
				icon.setIcon(blueIcon);
			}
		}
	}
	
	public void setNotColorBlind(){
		for(ShipStatusIcon icon : userShipStatusIcons){
			if(!icon.isSunk()){
				icon.setIcon(greenIcon);
			}
		}
		for(ShipStatusIcon icon : enemyShipStatusIcons){
			if(!icon.isSunk()){
				icon.setIcon(greenIcon);
			}
		}
	}
	
	public void setBackgroundColor(Color color){
		if(color == color.WHITE){
			ships1.setIcon(blackShips);
			ships2.setIcon(blackShips);
			commanderImage.setIcon(blackCommander);
		}else{ 
			ships1.setIcon(whiteShips);
			ships2.setIcon(whiteShips);
			commanderImage.setIcon(whiteCommander);
		}
		gamePanel.setBackground(color);
		this.setBackground(color);
	}
	
	public void setFontColor(Color color){
		rotate.setForeground(color);
		submit.setForeground(color);
		enemy.setForeground(color);
		home.setForeground(color);
		settings.setForeground(color);
		help.setForeground(color);
		username.setForeground(color);
		commander.setForeground(color);
		battleshipTitle.setForeground(color);
	}
	
	/* Initializes status icons (green dots) */
	private void initIcons(){ 
		userShipStatusIcons = new ShipStatusIcon[userBoard.numberOfShips()];
		enemyShipStatusIcons = new ShipStatusIcon[userBoard.numberOfShips()];
		userShipStatusIcons[0] = jLabel2;
		userShipStatusIcons[1] = jLabel5;
		userShipStatusIcons[2] = jLabel7;
		userShipStatusIcons[3] = jLabel8;
		enemyShipStatusIcons[0] = jLabel10;
		enemyShipStatusIcons[1] = jLabel11;
		enemyShipStatusIcons[2] = jLabel12;
		enemyShipStatusIcons[3] = jLabel13;
	}
	
	/** Starts a new thread to read a message from the server */
	public void newMessageThread(){
		Thread t = new Thread(new Runnable(){
        	public void run() {
		    		getMessage();
		    	}
			});
		t.start();
	}
	
	/** Starts the gameplay for both player one and player 2
		@param player the message from the server acknoledging the player number
	*/
	private void startGameplay(String message){
		enemyBoard.setClickable();
		if(message.contains("1")){
			if(DEBUG) System.out.println("Player one ready to play");
			this.newMessageThread(); //gets ok message
		}else{
			if(DEBUG) System.out.println("Player two ready to play");
			this.startDefense();
		}
	}
	
	/** Starts the defense turn for the user (changes game states, new thread to read message) */
	private void startDefense(){
		if(DEBUG) System.out.println("On defense");
		commander.setText("<html>Defend!</html>");
		attack = false;
		defend = true;
		this.newMessageThread(); //Gets message on defense
	}
	
	
	/** Starts the attack turn for the user (changes game states, commander message) */
	private void startOffense(){
		if(DEBUG) System.out.println("On offense");
		commander.setText("<html>Attack!</html>");
		attack = true;
		defend = false;
	}
	
	/**Send a message, either to the server or to the command prompt
		@param message the string message to send
	*/
	public void sendMessage(String message){
		client.send(message);
	}
	
	/**Recieves a message, either from the server or from the command line
		@return the recieved string message
	*/
	private void getMessage(){
		String message = client.receive();
		if(message.equals("ok")){
			this.startOffense();
		}else if(attack){
			if(!message.equals(ERROR)){
				playFX(message);
				enemyBoard.updateEnemyBoard(message);
				if(message.contains("win")) endGame(message);
				else this.startDefense();
			}	
		}else if(defend){
			playFX(message);
			userBoard.updateUserBoard(message);
			if(message.contains("loss")) endGame(message);
			else this.startOffense();
		}else if(settingShips){
			if(!message.equals(ERROR)){
				settingShips = false;
				gamePanel.remove(submit);
				gamePanel.remove(rotate);
				gamePanel.revalidate();
				gamePanel.repaint();
				this.startGameplay(message);
			}else{
				submit.setEnabled(true);
				rotate.setEnabled(true);
				commander.setText("<html>Error!<br>Try again!</html>");
				if(DEBUG) System.out.println("Error with ship locations");
			}
		}
	}
	
	private void playFX(String message){
		try{
			if(attack){
				if(message.contains("hit")){
					enemyHitClip.setFramePosition(0);
					enemyHitClip.start();
				}else{
					enemyMissClip.setFramePosition(0);
					enemyMissClip.start();
				}
			}else{
				if(message.contains("hit")){
					userHitClip.setFramePosition(0);
					userHitClip.start();
				}else{
					userMissClip.setFramePosition(0);	
					userMissClip.start();
				}
			}
		}catch(NullPointerException e){ }
	}
	
	public void updateEnemyStatusIcons(String ship){
		for(ShipStatusIcon icon : enemyShipStatusIcons){
			if((icon.getName()).equals(ship)){
				if(DEBUG) System.out.println("updating enemy ships icons");
				icon.setIcon(redIcon);
				icon.setSunk();
			}
		}
	}
	
	public void updateUserStatusIcons(String ship){
		for(ShipStatusIcon icon : userShipStatusIcons){
			if((icon.getName()).equals(ship)){
				if(DEBUG) System.out.println("updating yser ships icons");
				icon.setIcon(redIcon);
				icon.setSunk();
			}
		}
	}
	
	private void endGame(String message){
		if(DEBUG) System.out.println("Starting end game process");
		attack = false;
		defend = false;
		if(message.contains("win")) {} //TODO display popup
		client.startLeaderboards(message);
	}
	
    public void jButton5ActionPerformed(java.awt.event.ActionEvent evt) { //HOME
		client.displayHome();
	}

    public void jButton2ActionPerformed(java.awt.event.ActionEvent evt) { //HELP
		client.displayHelp();
	}

    public void jButton3ActionPerformed(java.awt.event.ActionEvent evt) { //SETTINGS
		client.displaySettings();
	}

	/** The submit button on the board, sends the ships location to the server and starts a new message thread
		@param evt the action event
	*/
    public void jButton4ActionPerformed(java.awt.event.ActionEvent evt) { //SUBMIT
		submit.setEnabled(false);
		rotate.setEnabled(false);
		userBoard.sendShipLocations();

	}

    public void jButton6ActionPerformed(java.awt.event.ActionEvent evt){ //ROTATING
		userBoard.rotate();
	}

	
	private void initComponents() {
        jPanel1 = new JPanel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel2 = new GridPanel(this);
        jPanel4 = new GridPanel(this);
        jLabel6 = new javax.swing.JLabel(); 
        jLabel2 = new ShipStatusIcon("AC"); //USER AC
        jLabel5 = new ShipStatusIcon("CR"); //USER CR
        jLabel7 = new ShipStatusIcon("SB"); //USER SB
        jLabel8 = new ShipStatusIcon("FR"); //USER FR
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new ShipStatusIcon("FR"); //USER AC
        jLabel11 = new ShipStatusIcon("SB"); //USER CR
        jLabel12 = new ShipStatusIcon("CR"); //USER SB
        jLabel13 = new ShipStatusIcon("AC"); //USER FR
        jLabel14 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(254, 254, 254));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setFont(new java.awt.Font("Cursor", 0, 12)); // NOI18N
        setName("Battleship"); // NOI18N

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setRequestFocusEnabled(false);

        jButton2.setBackground(new java.awt.Color(255, 255, 255));
        jButton2.setFont(new java.awt.Font("Orbitron", 0, 15)); // NOI18N
        jButton2.setText("help");
        jButton2.setBorderPainted(false);
        jButton2.setContentAreaFilled(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(255, 255, 255));
        jButton3.setFont(new java.awt.Font("Orbitron", 0, 15)); // NOI18N
        jButton3.setText("settings");
        jButton3.setBorderPainted(false);
        jButton3.setContentAreaFilled(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(255, 255, 255));
        jButton5.setFont(new java.awt.Font("Orbitron", 0, 15)); // NOI18N
        jButton5.setText("home");
        jButton5.setBorderPainted(false);
        jButton5.setContentAreaFilled(false);
        jButton5.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton5.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        jButton5.setName(""); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel3.setIcon((blackCommander)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Orbitron", 0, 11)); // NOI18N

        jPanel2.setBackground(new java.awt.Color(200, 200, 200));
        //jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(200, 200, 200)));
        jPanel2.setPreferredSize(new java.awt.Dimension(420, 420));
        //jPanel2.setLayout(new java.awt.GridLayout(10,10));

        jPanel4.setBackground(new java.awt.Color(200, 200, 200));
        //jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(200, 200, 200)));
        jPanel4.setPreferredSize(new java.awt.Dimension(420, 420));
        //jPanel4.setLayout(new java.awt.GridLayout(10,10));

        jLabel6.setFont(new java.awt.Font("Orbitron", 0, 11)); // NOI18N
        jLabel6.setIcon((blackShips)); // NOI18N

        jLabel2.setIcon( (greenIcon) ); // NOI18N
        jLabel2.setToolTipText("");

        jLabel5.setIcon((greenIcon)); // NOI18N
        jLabel5.setToolTipText("");

        jLabel7.setIcon((greenIcon)); // NOI18N
        jLabel7.setToolTipText("");

        jLabel8.setIcon((greenIcon)); // NOI18N
        jLabel8.setToolTipText("");

        jLabel9.setFont(new java.awt.Font("Orbitron", 0, 11)); // NOI18N
        jLabel9.setIcon((blackShips)); // NOI18N

        jLabel10.setIcon((greenIcon)); // NOI18N
        jLabel10.setToolTipText("");

        jLabel11.setIcon((greenIcon)); // NOI18N
        jLabel11.setToolTipText("");

        jLabel12.setIcon((greenIcon)); // NOI18N
        jLabel12.setToolTipText("");

        jLabel13.setIcon((greenIcon)); // NOI18N
        jLabel13.setToolTipText("");

        jLabel14.setFont(new java.awt.Font("Orbitron", 0, 24)); // NOI18N
        jLabel14.setText("Battleship");

        jButton4.setBackground(new java.awt.Color(255, 255, 255));
        jButton4.setFont(new java.awt.Font("Orbitron", 0, 12)); // NOI18N
        jButton4.setText("submit");
        jButton4.setBorderPainted(false);
        jButton4.setContentAreaFilled(false);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton6.setBackground(new java.awt.Color(255, 255, 255));
        jButton6.setFont(new java.awt.Font("Orbitron", 0, 12)); // NOI18N
        jButton6.setText("rotate");
        jButton6.setBorderPainted(false);
        jButton6.setContentAreaFilled(false);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Orbitron", 0, 24)); // NOI18N
        jLabel15.setText("Enemy");

        jLabel16.setFont(new java.awt.Font("Orbitron", 0, 24)); // NOI18N
        jLabel16.setText("username");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(21, 21, 21)
                            .addComponent(jLabel14)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jButton5)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jButton2)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jButton3))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(31, 31, 31)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel2)
                                        .addComponent(jLabel5)
                                        .addComponent(jLabel7)
                                        .addComponent(jLabel8))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jLabel6)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel16)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel3))
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel13)
                            .addComponent(jLabel12)
                            .addComponent(jLabel11)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel9)
                        .addGap(20, 20, 20)
                        .addComponent(jLabel15)))
                .addGap(32, 32, 32))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton5)
                            .addComponent(jButton2)
                            .addComponent(jButton3)
                            .addComponent(jLabel14))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel8)
                                        .addGap(15, 15, 15))
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addGap(18, 18, 18)
                                            .addComponent(jLabel3))
                                        .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                            .addComponent(jLabel13)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(jLabel12)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(jLabel11)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(jLabel10)
                                            .addGap(15, 15, 15))
                                        .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel16))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel15)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4)
                    .addComponent(jButton6)))
        );
    }	
}
