import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Initial extends JPanel implements ActionListener{
	private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
	private javax.swing.JButton jButton1;
	
	//Components
	private JPanel initialPanel; //Top level panel
	private Client client; //Parent
	private JPanel menu;
	private JLabel shipImage; 
	private JLabel battleshipTitle1;
	private JLabel battleshipTitle2;
	private JButton home;
	private JButton help;
	private JButton settings;
	private JButton register;
	private JButton login;

    public Initial(Client client){
		this.client = client;
		
		//Init NETBEANS components
        initComponents();
		
		//better names
		initialPanel = jPanel1;
		menu = jPanel2;
		shipImage = jLabel2;
		battleshipTitle1 = jLabel1;
		battleshipTitle2 = jLabel3;
		home = jButton5;
		help =  jButton2;
		settings = jButton3;
		
		//information boxes
		menu.setLayout(new GridLayout(2,1));
		register = new JButton("Register");
		login = new JButton("Login");
		register.setFont( new Font("Orbitron", 0, 15) );
		login.setFont( new Font("Orbitron", 0, 15) );
		menu.add(register);
		menu.add(login);

		this.addActionListeners();
		this.setBackgroundColor(client.getBackgroundColor());
		this.setFontColor(client.getFontColor());
		this.add(initialPanel);
    }
	
	public void formatTextField(JButton button){
		button.setFont( new Font("Orbitron", 0, 15) );
		button.setOpaque(false);
		button.setBorderPainted(true);
	}
	
	public void addActionListeners(){
		home.addActionListener(this);
		settings.addActionListener(this);
		help.addActionListener(this);
		register.addActionListener(this);
		login.addActionListener(this);
	}
	
	public void setFontColor(Color color){
		battleshipTitle1.setForeground(color);
		battleshipTitle2.setForeground(color);
		home.setForeground(color);
		help.setForeground(color);
		settings.setForeground(color);
		login.setForeground(color);
		login.setBorder(BorderFactory.createLineBorder(color));
		register.setForeground(color);
		register.setBorder(BorderFactory.createLineBorder(color));
	}
	
	public void setBackgroundColor(Color color){
		if(color == color.WHITE) shipImage.setIcon(client.blackShip);
		else shipImage.setIcon(client.whiteShip);
		this.setBackground(color);
		initialPanel.setBackground(color);
		login.setBackground(color);
		register.setBackground(color);
	}
	
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == register){
			//TODO
			client.startRegister();
		}else if(e.getSource() == login){
			//TODO
			client.startLogin();
		}else if(e.getSource() == home){
			client.displayHome();
		}else if(e.getSource() == help){
			client.displayHelp();
		}else if(e.getSource() == settings){
			client.displaySettings();
		}
	}


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jButton2.setBackground(new java.awt.Color(255, 255, 255));
        jButton2.setFont(new java.awt.Font("Orbitron", 0, 15)); // NOI18N
        jButton2.setText("help");
        jButton2.setBorderPainted(false);
        jButton2.setContentAreaFilled(false);

        jButton3.setBackground(new java.awt.Color(255, 255, 255));
        jButton3.setFont(new java.awt.Font("Orbitron", 0, 15)); // NOI18N
        jButton3.setText("settings");
        jButton3.setBorderPainted(false);
        jButton3.setContentAreaFilled(false);

        jLabel1.setFont(new java.awt.Font("Orbitron", 0, 24)); // NOI18N
        jLabel1.setText("Battleship");

        jButton5.setBackground(new java.awt.Color(255, 255, 255));
        jButton5.setFont(new java.awt.Font("Orbitron", 0, 15)); // NOI18N
        jButton5.setText("home");
        jButton5.setBorderPainted(false);
        jButton5.setContentAreaFilled(false);
        jButton5.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton5.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        jButton5.setName(""); // NOI18N

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel2.setOpaque(true);

        jLabel3.setFont(new java.awt.Font("Orbitron", 0, 60)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Battleship");

        jPanel2.setPreferredSize(new java.awt.Dimension(280, 40));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 280, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 120, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(371, 371, 371)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(371, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jButton3)
                    .addComponent(jButton2)
                    .addComponent(jButton5))
                .addGap(47, 47, 47)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(116, 116, 116)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
	}             
}
