package grime_client;

import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

/**
 * <p>
 * Title: GRIME - Generic RPG Instant Messenger Environment
 * </p>
 * <p>
 * Description: Client Interface For GRIME
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company: N/A
 * </p>
 *
 * @author Megaspaz
 * @version 1.0
 */
@SuppressWarnings("deprecation")
public class Client_Frame extends JFrame implements Runnable {
  private static final long serialVersionUID = 1L;
  private JPanel contentPane;
  private JLabel jLabel1 = new JLabel();
  private JPanel connect_panel = new JPanel();
  private JLabel jLabel2 = new JLabel();
  private JPanel players_panel = new JPanel();
  private JLabel jLabel3 = new JLabel();
  private JPanel dice_panel = new JPanel();
  private JLabel jLabel4 = new JLabel();
  private JPanel chat_panel = new JPanel();
  private JMenuBar jMenuBar1 = new JMenuBar();
  private JMenu jMenu1 = new JMenu();
  private JMenuItem jMenuItem1 = new JMenuItem();
  private JMenuItem jMenuItem2 = new JMenuItem();
  private JMenu jMenu2 = new JMenu();
  private JMenuItem jMenuItem3 = new JMenuItem();
  private JLabel jLabel5 = new JLabel();
  private JTextField jTextField_url = new JTextField();
  private JButton jButton_connect = new JButton();
  private JButton jButton_disconnect = new JButton();
  private JScrollPane jScrollPane1 = new JScrollPane();
  private JTextArea jTextArea_chat_display = new JTextArea();
  private JLabel jLabel6 = new JLabel();
  private JLabel jLabel7 = new JLabel();
  private JScrollPane jScrollPane2 = new JScrollPane();
  private JTextArea jTextArea_chat_input = new JTextArea();
  private JButton jButton_send = new JButton();
  private JScrollPane jScrollPane3 = new JScrollPane();
  private JScrollPane jScrollPane4 = new JScrollPane();
  private JLabel jLabel8 = new JLabel();
  private JTextArea jTextArea_roll_results = new JTextArea();
  private JButton jButton_roll = new JButton();
  private JLabel jLabel9 = new JLabel();
  private JTextField jTextField_numdie = new JTextField();
  private JList<String> jList_players = new JList<String>();
  private JLabel jLabel10 = new JLabel();
  private JTextField jTextField_player_alias = new JTextField();
  private JButton jButton_clear = new JButton();
  private JButton jButton_clear_dice = new JButton();
  // set variables for processing data to/from server
  private String strTitle = "Input Error";
  private String strErrorMsg = "";
  private JFrame frame = new JFrame();
  private BufferedWriter output;
  private BufferedReader input;
  private String alias;
  private String url;
  private Socket client;
  private volatile Thread pThread;

  // Construct the frame
  public Client_Frame() {
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try {
      jbInit();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  // Component initialization
  private void jbInit() throws Exception {
    contentPane = (JPanel) this.getContentPane();
    setIconImage(Toolkit.getDefaultToolkit().createImage(Client_Frame.class.getResource("/vj_blood.png")));

    jLabel1.setForeground(Color.black);
    jLabel1.setRequestFocusEnabled(false);
    jLabel1.setText("Connect");
    jLabel1.setBounds(new Rectangle(7, 8, 58, 19));
    contentPane.setMaximumSize(new Dimension(700, 585));
    contentPane.setMinimumSize(new Dimension(700, 585));
    contentPane.setNextFocusableComponent(null);
    contentPane.setRequestFocusEnabled(false);
    contentPane.setLayout(null);
    this.setJMenuBar(jMenuBar1);
    this.setResizable(false);
    this.setSize(new Dimension(700, 585));
    this.setTitle("GRIME - Generic RPG Instant Messenger Environment");
    // make this a non-resizable window frame
    connect_panel.setBorder(BorderFactory.createEtchedBorder());
    connect_panel.setRequestFocusEnabled(false);
    connect_panel.setBounds(new Rectangle(7, 28, 462, 129));
    connect_panel.setLayout(null);
    jLabel2.setRequestFocusEnabled(false);
    jLabel2.setText("Players List");
    jLabel2.setBounds(new Rectangle(479, 8, 76, 19));
    players_panel.setBorder(BorderFactory.createEtchedBorder());
    players_panel.setRequestFocusEnabled(false);
    players_panel.setBounds(new Rectangle(479, 28, 207, 129));
    players_panel.setLayout(null);
    jLabel3.setRequestFocusEnabled(false);
    jLabel3.setText("Dice Rolls");
    jLabel3.setBounds(new Rectangle(479, 177, 70, 19));
    dice_panel.setBorder(BorderFactory.createEtchedBorder());
    dice_panel.setRequestFocusEnabled(false);
    dice_panel.setBounds(new Rectangle(479, 197, 207, 334));
    dice_panel.setLayout(null);
    jLabel4.setRequestFocusEnabled(false);
    jLabel4.setText("Communications Window");
    jLabel4.setBounds(new Rectangle(7, 177, 165, 19));
    chat_panel.setBorder(BorderFactory.createEtchedBorder());
    chat_panel.setRequestFocusEnabled(false);
    chat_panel.setBounds(new Rectangle(7, 197, 462, 334));
    chat_panel.setLayout(null);
    jMenu1.setText("File");
    jMenu1.setMnemonic('F');
    jMenuItem1.setText("Disconnect");
    jMenuItem1.addActionListener(new Client_Frame_jMenuItem1_actionAdapter(this));
    jMenuItem1.setEnabled(false);
    jMenuItem1.setMnemonic('D');
    jMenuItem2.setText("Exit");
    jMenuItem2.addActionListener(new Client_Frame_jMenuItem2_actionAdapter(this));
    jMenuItem2.setMnemonic('x');
    jMenu2.setText("Help");
    jMenu2.setMnemonic('H');
    jMenuItem3.setText("About");
    jMenuItem3.addActionListener(new Client_Frame_jMenuItem3_actionAdapter(this));
    jMenuItem3.setMnemonic('A');
    jMenuBar1.setBorder(null);
    jLabel5.setLabelFor(jTextField_url);
    jLabel5.setText("Internet URL");
    jLabel5.setDisplayedMnemonic('U');
    jLabel5.setBounds(new Rectangle(7, 69, 82, 15));
    jTextField_url.setBorder(BorderFactory.createLoweredBevelBorder());
    jTextField_url.setNextFocusableComponent(jButton_connect);
    jTextField_url.setText("");
    jTextField_url.setBounds(new Rectangle(7, 89, 224, 24));
    jTextField_url.setFocusable(true);
    jTextField_url.addActionListener(new Client_Frame_jTextField_url_actionAdapter(this));
    jButton_connect.setBounds(new Rectangle(238, 69, 104, 44));
    jButton_connect.setBorder(BorderFactory.createRaisedBevelBorder());
    jButton_connect.setNextFocusableComponent(jButton_disconnect);
    jButton_connect.setText("Connect");
    jButton_connect.setFocusable(true);
    jButton_connect.addActionListener(new Client_Frame_jButton_connect_actionAdapter(this));
    jButton_connect.setMnemonic('C');
    jButton_disconnect.setText("Disconnect");
    jButton_disconnect.setMnemonic('D');
    jButton_disconnect.setBounds(new Rectangle(350, 69, 104, 44));
    jButton_disconnect.setEnabled(false);
    jButton_disconnect.setBorder(BorderFactory.createRaisedBevelBorder());
    jButton_disconnect.setNextFocusableComponent(jTextArea_chat_input);
    jButton_disconnect.setText("Disconnect");
    jButton_disconnect.setFocusable(false);
    jButton_disconnect.addActionListener(new Client_Frame_jButton_disconnect_actionAdapter(this));
    jScrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    jScrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    jScrollPane1.setEnabled(true);
    jScrollPane1.setRequestFocusEnabled(false);
    jScrollPane1.setBounds(new Rectangle(8, 32, 447, 182));
    jTextArea_chat_display.setBackground(Color.white);
    jTextArea_chat_display.setForeground(Color.black);
    jTextArea_chat_display.setRequestFocusEnabled(true);
    jTextArea_chat_display.setCaretColor(Color.black);
    jTextArea_chat_display.setEditable(false);
    jTextArea_chat_display.setText("");
    jTextArea_chat_display.setLineWrap(true);
    jTextArea_chat_display.setWrapStyleWord(true);
    jLabel6.setRequestFocusEnabled(false);
    jLabel6.setText("Communications Monitor");
    jLabel6.setBounds(new Rectangle(8, 8, 168, 16));
    jLabel7.setText("User Input");
    jLabel7.setDisplayedMnemonic('I');
    jLabel7.setLabelFor(jTextArea_chat_input);
    jLabel7.setBounds(new Rectangle(8, 224, 70, 17));
    jScrollPane2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    jScrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    jScrollPane2.setBounds(new Rectangle(8, 247, 331, 79));
    jTextArea_chat_input.setEnabled(false);
    jTextArea_chat_input.setNextFocusableComponent(jButton_send);
    jTextArea_chat_input.setText("");
    jTextArea_chat_input.setLineWrap(true);
    jTextArea_chat_input.setWrapStyleWord(true);
    jTextArea_chat_input.addKeyListener(new Client_Frame_jTextArea_chat_input_keyAdapter(this));
    jButton_send.setMnemonic('S');
    jButton_send.setText("Send");
    jButton_send.setFocusable(false);
    jButton_send.addActionListener(new Client_Frame_jButton_send_actionAdapter(this));
    jButton_send.setBorder(BorderFactory.createRaisedBevelBorder());
    jButton_send.setNextFocusableComponent(jButton_clear);
    jButton_send.setBounds(new Rectangle(351, 246, 104, 37));
    jButton_send.setEnabled(false);

    jScrollPane3.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    jScrollPane3.setRequestFocusEnabled(false);
    jScrollPane3.setBounds(new Rectangle(7, 7, 193, 107));
    jScrollPane4.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    jScrollPane4.setRequestFocusEnabled(false);
    jScrollPane4.setBounds(new Rectangle(7, 32, 193, 183));
    jLabel8.setRequestFocusEnabled(false);
    jLabel8.setText("Roll Results");
    jLabel8.setBounds(new Rectangle(8, 9, 78, 15));
    jTextArea_roll_results.setEditable(false);
    jTextArea_roll_results.setText("");
    jTextArea_roll_results.setLineWrap(false);
    jTextArea_roll_results.setWrapStyleWord(false);
    jButton_roll.setBounds(new Rectangle(96, 280, 104, 44));
    jButton_roll.setEnabled(false);
    jButton_roll.setBorder(BorderFactory.createRaisedBevelBorder());
    jButton_roll.setNextFocusableComponent(jTextField_player_alias);
    jButton_roll.setOpaque(true);
    jButton_roll.setFocusable(true);
    jButton_roll.addActionListener(new Client_Frame_jButton_roll_actionAdapter(this));
    jButton_roll.setText("Roll");
    jButton_roll.setMnemonic('R');
    jLabel9.setBounds(new Rectangle(7, 280, 78, 15));
    jLabel9.setText("Num Of Die");
    jLabel9.setRequestFocusEnabled(false);
    jLabel9.setDisplayedMnemonic('N');
    jLabel9.setLabelFor(jTextField_numdie);
    jTextField_numdie.setEnabled(false);
    jTextField_numdie.setBorder(BorderFactory.createLoweredBevelBorder());
    jTextField_numdie.setNextFocusableComponent(jButton_roll);
    jTextField_numdie.setText("");
    jTextField_numdie.setBounds(new Rectangle(7, 300, 78, 24));
    jTextField_numdie.addActionListener(new Client_Frame_jTextField_numdie_actionAdapter(this));
    jList_players.setForeground(Color.black);
    jList_players.setRequestFocusEnabled(false);
    jList_players.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    jLabel10.setBounds(new Rectangle(7, 7, 82, 15));
    jLabel10.setDisplayedMnemonic('U');
    jLabel10.setText("Player Alias");
    jLabel10.setRequestFocusEnabled(true);
    jLabel10.setDisplayedMnemonic('P');
    jLabel10.setLabelFor(jTextField_player_alias);
    jTextField_player_alias.setFocusable(true);
    jTextField_player_alias.addActionListener(new Client_Frame_jTextField_player_alias_actionAdapter(this));
    jTextField_player_alias.setBounds(new Rectangle(7, 31, 224, 24));
    jTextField_player_alias.setText("");
    jTextField_player_alias.setNextFocusableComponent(jTextField_url);
    jTextField_player_alias.setBorder(BorderFactory.createLoweredBevelBorder());
    jButton_clear.setBounds(new Rectangle(351, 288, 104, 37));
    jButton_clear.setEnabled(false);
    jButton_clear.setNextFocusableComponent(jTextField_numdie);
    jButton_clear.setBorder(BorderFactory.createRaisedBevelBorder());
    jButton_clear.setText("Clear Monitor");
    jButton_clear.setFocusable(false);
    jButton_clear.addActionListener(new Client_Frame_jButton_clear_actionAdapter(this));
    jButton_clear.setMnemonic('L');
    jButton_clear_dice.setBounds(new Rectangle(7, 220, 193, 30));
    jButton_clear_dice.setEnabled(false);
    jButton_clear_dice.setBorder(BorderFactory.createRaisedBevelBorder());
    jButton_clear_dice.setText("Clear Dice Results");
    jButton_clear_dice.addActionListener(new Client_Frame_jButton_clear_dice_actionAdapter(this));
    dice_panel.add(jScrollPane4, null);
    dice_panel.add(jLabel8, null);
    dice_panel.add(jButton_roll, null);
    dice_panel.add(jLabel9, null);
    dice_panel.add(jTextField_numdie, null);
    dice_panel.add(jButton_clear_dice, null);
    contentPane.add(jLabel1, null);
    contentPane.add(jLabel2, null);
    jScrollPane4.getViewport().add(jTextArea_roll_results, null);
    chat_panel.add(jScrollPane1, null);
    chat_panel.add(jLabel6, null);
    chat_panel.add(jScrollPane2, null);
    chat_panel.add(jLabel7, null);
    chat_panel.add(jButton_send, null);
    chat_panel.add(jButton_clear, null);
    jScrollPane2.getViewport().add(jTextArea_chat_input, null);
    contentPane.add(jLabel3, null);
    contentPane.add(dice_panel, null);
    jScrollPane1.getViewport().add(jTextArea_chat_display, null);
    contentPane.add(players_panel, null);
    players_panel.add(jScrollPane3, null);
    contentPane.add(connect_panel, null);
    jScrollPane3.getViewport().add(jList_players, null);
    contentPane.add(jLabel4, null);
    jMenuBar1.add(jMenu1);
    jMenuBar1.add(jMenu2);
    jMenu1.add(jMenuItem1);
    jMenu1.add(jMenuItem2);
    jMenu2.add(jMenuItem3);
    connect_panel.add(jLabel5, null);
    connect_panel.add(jButton_connect, null);
    connect_panel.add(jButton_disconnect, null);
    connect_panel.add(jTextField_url, null);
    connect_panel.add(jLabel10, null);
    connect_panel.add(jTextField_player_alias, null);
    contentPane.add(chat_panel, null);
    // set default focus on program open
    jTextField_player_alias.requestFocus();

  }

  // Overridden so we can exit when window is closed in title bar
  // like clicking the close button ("X") in corner of title bar or
  // by using the close window key combo to close program window (ctrl-F4)
  @Override
  protected void processWindowEvent(WindowEvent e) {
    super.processWindowEvent(e);
    if (e.getID() == WindowEvent.WINDOW_CLOSING) {
      if (true == jButton_disconnect.isEnabled()) // this means person is
      // connected
      {
        closeConnection();
      }
      System.exit(0);
    }
  }

  // exit program menu item in File
  void jMenuItem2_actionPerformed(ActionEvent e) {
    if (true == jButton_disconnect.isEnabled()) // this means person is
    // connected
    {
      closeConnection();
    }
    System.exit(0);
  }

  // help menu item in Help
  void jMenuItem3_actionPerformed(ActionEvent e) {
    frmAbout dlg = new frmAbout(this);
    Dimension dlgSize = dlg.getPreferredSize();
    Dimension frmSize = getSize();
    Point loc = getLocation();
    dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
    dlg.setModal(true);
    dlg.pack();
    dlg.setVisible(true);
  }

  void jButton_connect_actionPerformed(ActionEvent e) {
    url = jTextField_url.getText().trim().toString();
    alias = jTextField_player_alias.getText().trim().toString();
    try {
      if (0 == url.trim().length()) {
        strErrorMsg = "URL is needed to connect to server";
        JOptionPane.showMessageDialog(frame, strErrorMsg.toString(), strTitle.toString(),
            JOptionPane.INFORMATION_MESSAGE);
        jTextField_url.setText("");
        jTextField_url.requestFocus();
      } else if (0 == alias.trim().length()) {
        strErrorMsg = "You must provide a player alias";
        JOptionPane.showMessageDialog(frame, strErrorMsg.toString(), strTitle.toString(),
            JOptionPane.INFORMATION_MESSAGE);
        jTextField_player_alias.setText("");
        jTextField_player_alias.requestFocus();
      } else {
        // check if user alias has illegal characters. for this
        // implementation
        // illegal characters are " " and "\"
        boolean IsIllegal = false;
        for (int i = 0; i < alias.length(); i++) {
          if ('\\' == alias.charAt(i) || ' ' == alias.charAt(i)) {
            IsIllegal = true;
            break;
          }
        }
        if (true == IsIllegal) {
          strErrorMsg = "Player alias cannot include spaces or \"\\\"";
          JOptionPane.showMessageDialog(frame, strErrorMsg.toString(), strTitle.toString(),
              JOptionPane.INFORMATION_MESSAGE);
          jTextField_player_alias.setText("");
          jTextField_player_alias.requestFocus();

        } else {
          client = new Socket(InetAddress.getByName(url), 5000);
          input = new BufferedReader(new InputStreamReader(client.getInputStream()));
          output = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));

          pThread = new Thread(this);
          pThread.start();

          updateMonitor(input.readLine() + "\n"); // synchronized
          // method
          output.write(alias);
          output.newLine();
          output.flush();

          enableChatWidgets();
          enableDisconnect();
          enableDiceWidgets();
          disableConnectWidgets();

        }
      }
    } catch (Exception ex) {
      // i've only seen a connection refused error
      strErrorMsg = "Most likely due to the game server being down\n" + "or you have entered an invalid URL .";
      JOptionPane.showMessageDialog(frame, "An error has occurred.\n" + strErrorMsg, "Error",
          JOptionPane.INFORMATION_MESSAGE);
      disableChatWidgets();
      disableDisconnect();
      disableDiceWidgets();
      enableConnectWidgets();
    }
  }

  /**
   * Overridden run method for the Thread class
   *
   * communication/command type: 1 = player list update - server sends to clients
   * 2 = regular chat - basic communication to all players 3 = dice roll update -
   * all players 4 = player/client leave & disconnect - client to server. unused
   * by server but server will use 1 & 2 to update other players after getting #4
   * command from client 5 = server forces single client to close - verify player
   * alias fails 6 = server forces all clients to close - admin closes server File
   * > Exit
   */
  @Override
  public void run() {
    int close_type = 0;
    Thread thisThread = Thread.currentThread();
    while (pThread == thisThread) {
      String stream;
      try {
        stream = input.readLine().toString();
        if ('6' == stream.charAt(0)) {
          JOptionPane.showMessageDialog(frame, stream.substring(1).toString(), "Alert!",
              JOptionPane.INFORMATION_MESSAGE);
          close_type = 1;
          break;
        } else if ('5' == stream.charAt(0)) {
          JOptionPane.showMessageDialog(frame, stream.substring(1).toString(), "Error",
              JOptionPane.INFORMATION_MESSAGE);
          close_type = 0;
          break;
        } else if ('3' == stream.charAt(0)) {
          StringTokenizer strtok = new StringTokenizer(stream.substring(1));
          String temp = "";
          while (strtok.hasMoreElements()) {
            temp += strtok.nextToken("\\") + "\n";
          }
          // update dice results
          updateDice(temp); // synchronized method
        } else if ('1' == stream.charAt(0)) {
          // update player list by tokeninizing the stream
          Vector<String> player_vector = new Vector<String>();
          StringTokenizer strtok = new StringTokenizer(stream.substring(1));
          while (strtok.hasMoreTokens()) {
            player_vector.addElement(strtok.nextToken("\\"));
          }
          // update the players list
          jList_players.setListData(player_vector);
        } else {
          updateMonitor(stream); // synchronized method
        }
        // autoscroll down display monitors
        jTextArea_chat_display.setCaretPosition(jTextArea_chat_display.getText().length());
        jTextArea_roll_results.setCaretPosition(jTextArea_roll_results.getText().length());
      } catch (Exception e) {
        // do nothing
      }
    }
    // clear, disable, and enable widgets depending on close_type
    if (1 == close_type) {
      // clear dice and player list
      clearDice();
      clearPlayersList();
    }
    disableDisconnect();
    disableChatWidgets();
    disableDiceWidgets();
    enableConnectWidgets();
    // close connection
    forcedClose();
    // stop this thread. maybe unneeded
    pThread.interrupt();
  }

  /** this method updates the chat monitor */
  synchronized void sendData() {
    try {
      output.write("2" + alias + " >>> " + jTextArea_chat_input.getText().trim().toString());
      output.newLine();
      output.flush();
      jTextArea_chat_input.setText("");
      jTextArea_chat_input.requestFocus();
    } catch (Exception e) {
      // do nothing
    }
  }

  /** This method is a forced close from the server */
  // this method is not synchronized (at the moment) since the overridden run
  // method is broken out of
  // with a break; so the thread will not process any other input. this also
  // seems to
  // be the only way out of all the ways i called this (whether breaking from
  // run or
  // synchronizing this method and calling it in run) that doesn't freeze on a
  // disconnect
  void forcedClose() {
    if (null != client) {
      try {
        // close streams and disconnect
        output.close();
        input.close();
        client.close();
      } catch (Exception e) {
        strErrorMsg = "Shutting down program.";
        JOptionPane.showMessageDialog(frame, "An unknown error has occurred. " + strErrorMsg, "Error",
            JOptionPane.INFORMATION_MESSAGE);
        System.exit(1);
      }
      // deallocate socket just in case
      client = null;
      output = null;
      input = null;
    }
  }

  /**
   * This method performs duties for disconnecting to the server - user initiated
   * disconnect
   */
  synchronized void closeConnection() {
    if (null != client) {
      try {
        // inform the the server and then others that this player is
        // disconnecting
        output.write("4" + alias);
        output.newLine();
        output.flush();
        output.close();
        input.close();
        client.close();
      } catch (Exception e) {
        strErrorMsg = "Shutting down program.";
        JOptionPane.showMessageDialog(frame, "An unknown error has occurred. " + strErrorMsg, "Error",
            JOptionPane.INFORMATION_MESSAGE);
        System.exit(1);
      }
      // deallocate socket just in case
      client = null;
      output = null;
      input = null;
      // clear, enable, disable widgets
      disableDisconnect();
      disableChatWidgets();
      disableDiceWidgets();
      clearPlayersList();
      clearDice();
      enableConnectWidgets();
      Thread tempThread = pThread;
      pThread = null;
      if (tempThread != null) {
        tempThread.interrupt();
      }
    }
  }

  /**
   * This method clears the Dice Roll monitor which is synchronized when player
   * connected
   */
  synchronized void sync_clearDice() {
    jTextArea_roll_results.setText("");
    jTextField_numdie.setText("");
  }

  /** This method clears the chat display */
  synchronized void clearMonitor() {
    jTextArea_chat_display.setText("");
  }

  /** This method updates the chat display */
  synchronized void updateMonitor(String datatext) {
    jTextArea_chat_display.append(datatext.substring(1) + "\n");
  }

  /** This method updates the dice roll results display */
  synchronized void updateDice(String diceresults) {
    jTextArea_roll_results.append(diceresults + "\n");

  }

  /** This method clears the Dice Roll monitor on disconnect */
  void clearDice() {
    jTextArea_roll_results.setText("");
    jTextField_numdie.setText("");
  }

  /** This method clears out the players list */
  void clearPlayersList() {
    Vector<String> clean_vector = new Vector<String>();
    jList_players.setListData(clean_vector);
  }

  /** Enable the widgets in the chat panel */
  void enableChatWidgets() {
    jButton_send.setEnabled(true);
    jButton_clear.setEnabled(true);
    jTextArea_chat_input.setEnabled(true);
    jTextArea_chat_input.requestFocus();
  }

  /** Enable the widgets in the connection panel */
  void enableConnectWidgets() {
    jButton_connect.setEnabled(true);
    jTextField_url.setEnabled(true);
    jTextField_player_alias.setEnabled(true);
    jTextField_url.setText("");
    jTextField_player_alias.setText("");
    jTextField_player_alias.requestFocus();
  }

  /** Disables the widgets in the chat panel */
  void disableChatWidgets() {
    jButton_send.setEnabled(false);
    jButton_clear.setEnabled(false);
    jTextArea_chat_input.setEnabled(false);
    jTextArea_chat_display.setText("");
    jTextArea_chat_input.setText("");
  }

  /** Disable the widgets in the connection panel */
  void disableConnectWidgets() {
    jButton_connect.setEnabled(false);
    jTextField_url.setEnabled(false);
    jTextField_player_alias.setEnabled(false);
  }

  /** Enable the widgets in the dice roll panel */
  void enableDiceWidgets() {
    jButton_roll.setEnabled(true);
    jButton_clear_dice.setEnabled(true);
    jTextField_numdie.setEnabled(true);
  }

  /** Disable the widgets in the dice roll panel */
  void disableDiceWidgets() {
    jButton_roll.setEnabled(false);
    jButton_clear_dice.setEnabled(false);
    jTextField_numdie.setEnabled(false);
  }

  /** Enable Disconnect button */
  void enableDisconnect() {
    jButton_disconnect.setEnabled(true);
    jMenuItem1.setEnabled(true);
  }

  /** Disable Disconnect button */
  void disableDisconnect() {
    jButton_disconnect.setEnabled(false);
    jMenuItem1.setEnabled(false);
  }

  // send chat data to server
  void jButton_send_actionPerformed(ActionEvent e) {
    // regular chat stream
    sendData(); // synchronized method
  }

  // key trap on enter to send chat data to server
  void jTextArea_chat_input_keyPressed(KeyEvent e) {
    if (e.getKeyChar() == '\n') {
      // regular chat stream
      sendData(); // synchronized method
    }
  }

  // after key trap, this sets focus and clears chat input
  void jTextArea_chat_input_keyReleased(KeyEvent e) {
    // set the caret back to the top of the chat input textarea
    if (e.getKeyChar() == '\n') {
      jTextArea_chat_input.setText("");
      jTextArea_chat_input.requestFocus();
    }
  }

  // clears the chat monitor
  void jButton_clear_actionPerformed(ActionEvent e) {
    clearMonitor(); // synchronized method
    // set focus back to chat input
    jTextArea_chat_input.requestFocus();
  }

  // on enter key press, calls the Connect button's action method
  void jTextField_player_alias_actionPerformed(ActionEvent e) {
    // call jButton_connect's processing event
    jButton_connect_actionPerformed(e);
  }

  // on enter key press, calls the Connect button's action method
  void jTextField_url_actionPerformed(ActionEvent e) {
    // call jButton_connect's processing event
    jButton_connect_actionPerformed(e);
  }

  // closes the connection to server
  void jButton_disconnect_actionPerformed(ActionEvent e) {
    closeConnection(); // synchronized method
    // set default focus
    jTextField_player_alias.requestFocus();
  }

  // simulates dice rolls and sends results to server
  void jButton_roll_actionPerformed(ActionEvent e) {
    // todo - dice rolling function(s)
    int number_of_dice;
    int roll_results[] = { 0, 0, 0, 0, 0, 0, 0 }; // going to ignore element
    // 0
    String usr_in = jTextField_numdie.getText().trim().toString();
    String out_str = "";
    if (0 == usr_in.length()) {
      number_of_dice = 1;
    } else {
      // need to check if input is numeric.
      try {
        // try and parse an integer out of the string using base 10
        // will throw an error if not completely numeric. in that case
        // use default of 1 dice
        number_of_dice = Integer.parseInt(usr_in, 10);
        if (number_of_dice < 0) {
          number_of_dice = 1;
        }
      } catch (NumberFormatException ex) {
        // user entered non numeric input. using default value
        number_of_dice = 1;
      }
    }
    /*
     * debug stuff Integer dice_num = new Integer (number_of_dice);
     * jTextArea_roll_results.append(dice_num.toString() + "\n\n");
     */
    // do dice rolls and store results
    for (int i = 1; i <= number_of_dice; i++) {
      int index = 1 + (int) (Math.random() * 6);
      roll_results[index]++;
    }
    // format and setup output stream
    out_str = "3" + alias + " rolls " + number_of_dice + " dice. Results: \\";
    for (int i = 1; i < roll_results.length; i++) {
      out_str += i + "'s: " + roll_results[i] + "\\";
    }
    // trim final "\" off
    out_str = out_str.substring(0, out_str.length() - 1);
    // send output to server for distrobution
    try {
      output.write(out_str);
      output.newLine();
      output.flush();
    } catch (Exception ex) {
      strErrorMsg = "Shutting down program.";
      JOptionPane.showMessageDialog(frame, "An unknown error Has occurred. " + strErrorMsg, "Error",
          JOptionPane.INFORMATION_MESSAGE);
      System.exit(1);
    }
    // clear out any numbers in number of dice stuff
    jTextField_numdie.setText("");
    // transfer focus to chat input
    jTextArea_chat_input.requestFocus();
  }

  // on enter key press calls Rolls button's action method
  void jTextField_numdie_actionPerformed(ActionEvent e) {
    // call the jButton_roll's processing event.
    jButton_roll_actionPerformed(e);
    jTextField_numdie.setText("");
    // set focus back to chat input
    jTextArea_chat_input.requestFocus();
  }

  // clears the dice rolls monitor
  void jButton_clear_dice_actionPerformed(ActionEvent e) {
    // use the synchronized clear dice method to avoid race conditions
    sync_clearDice(); // synchronized method
    jTextArea_chat_input.requestFocus();
  }

  // Disconnect menu item in File - calls Disconnect button's action method
  void jMenuItem1_actionPerformed(ActionEvent e) {
    // call the disconnect button function
    jButton_disconnect_actionPerformed(e);
  }

}

class Client_Frame_jMenuItem2_actionAdapter implements java.awt.event.ActionListener {
  Client_Frame adaptee;

  Client_Frame_jMenuItem2_actionAdapter(Client_Frame adaptee) {
    this.adaptee = adaptee;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    adaptee.jMenuItem2_actionPerformed(e);
  }
}

class Client_Frame_jMenuItem3_actionAdapter implements java.awt.event.ActionListener {
  Client_Frame adaptee;

  Client_Frame_jMenuItem3_actionAdapter(Client_Frame adaptee) {
    this.adaptee = adaptee;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    adaptee.jMenuItem3_actionPerformed(e);
  }
}

class Client_Frame_jButton_connect_actionAdapter implements java.awt.event.ActionListener {
  Client_Frame adaptee;

  Client_Frame_jButton_connect_actionAdapter(Client_Frame adaptee) {
    this.adaptee = adaptee;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    adaptee.jButton_connect_actionPerformed(e);
  }
}

class Client_Frame_jButton_send_actionAdapter implements java.awt.event.ActionListener {
  Client_Frame adaptee;

  Client_Frame_jButton_send_actionAdapter(Client_Frame adaptee) {
    this.adaptee = adaptee;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    adaptee.jButton_send_actionPerformed(e);
  }
}

class Client_Frame_jTextArea_chat_input_keyAdapter extends java.awt.event.KeyAdapter {
  Client_Frame adaptee;

  Client_Frame_jTextArea_chat_input_keyAdapter(Client_Frame adaptee) {
    this.adaptee = adaptee;
  }

  @Override
  public void keyPressed(KeyEvent e) {
    adaptee.jTextArea_chat_input_keyPressed(e);
  }

  @Override
  public void keyReleased(KeyEvent e) {
    adaptee.jTextArea_chat_input_keyReleased(e);
  }
}

class Client_Frame_jButton_clear_actionAdapter implements java.awt.event.ActionListener {
  Client_Frame adaptee;

  Client_Frame_jButton_clear_actionAdapter(Client_Frame adaptee) {
    this.adaptee = adaptee;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    adaptee.jButton_clear_actionPerformed(e);
  }
}

class Client_Frame_jTextField_player_alias_actionAdapter implements java.awt.event.ActionListener {
  Client_Frame adaptee;

  Client_Frame_jTextField_player_alias_actionAdapter(Client_Frame adaptee) {
    this.adaptee = adaptee;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    adaptee.jTextField_player_alias_actionPerformed(e);
  }
}

class Client_Frame_jTextField_url_actionAdapter implements java.awt.event.ActionListener {
  Client_Frame adaptee;

  Client_Frame_jTextField_url_actionAdapter(Client_Frame adaptee) {
    this.adaptee = adaptee;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    adaptee.jTextField_url_actionPerformed(e);
  }
}

class Client_Frame_jButton_disconnect_actionAdapter implements java.awt.event.ActionListener {
  Client_Frame adaptee;

  Client_Frame_jButton_disconnect_actionAdapter(Client_Frame adaptee) {
    this.adaptee = adaptee;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    adaptee.jButton_disconnect_actionPerformed(e);
  }
}

class Client_Frame_jButton_roll_actionAdapter implements java.awt.event.ActionListener {
  Client_Frame adaptee;

  Client_Frame_jButton_roll_actionAdapter(Client_Frame adaptee) {
    this.adaptee = adaptee;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    adaptee.jButton_roll_actionPerformed(e);
  }
}

class Client_Frame_jTextField_numdie_actionAdapter implements java.awt.event.ActionListener {
  Client_Frame adaptee;

  Client_Frame_jTextField_numdie_actionAdapter(Client_Frame adaptee) {
    this.adaptee = adaptee;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    adaptee.jTextField_numdie_actionPerformed(e);
  }
}

class Client_Frame_jButton_clear_dice_actionAdapter implements java.awt.event.ActionListener {
  Client_Frame adaptee;

  Client_Frame_jButton_clear_dice_actionAdapter(Client_Frame adaptee) {
    this.adaptee = adaptee;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    adaptee.jButton_clear_dice_actionPerformed(e);
  }
}

class Client_Frame_jMenuItem1_actionAdapter implements java.awt.event.ActionListener {
  Client_Frame adaptee;

  Client_Frame_jMenuItem1_actionAdapter(Client_Frame adaptee) {
    this.adaptee = adaptee;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    adaptee.jMenuItem1_actionPerformed(e);
  }
}