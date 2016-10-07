import java.awt.Container;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import javax.swing.border.BevelBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;

//klasa koja opisuje GUI
//sve će se nalaziti u glavnom container-u MyFrame
public class MyFrame extends JFrame{
    //stvara JTextArea veličine 10×47 koji služi za prikaz primljenih i poslanih poruka
    JTextArea ChatBox=new JTextArea(10,47);
    //kreira novi JScrollPane koji se sastoji od ChatBox-a i
    //vertikalnog i horizontalnoh ScrollBar-a za ChatBox koji se uvijek prikazuje
    JScrollPane myChatHistory=new JScrollPane(ChatBox,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
        JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    //stvara JTextArea veličine 5×40 koji služi za unošenje poruka koje korisnik želi poslati
    JTextArea UserText = new JTextArea(5,40);
    //kreirano novi JScrollPane koji se sastoji od UserText-a i
    //vertikalnog i horizontalnog ScrollBar-a za UserText koji se prikazuje
    //ako je uneseni tekst veći od granica UserText-a
    JScrollPane myUserHistory=new JScrollPane(UserText,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    //stvara novi gumb Send s imenom "Send to"
    JButton Send = new JButton("Send to");
    //stvara novi JTextField koji služi za unošenje imena korisnika kojima se želi poslati poruka
    JTextField User=new JTextField(38);
    //stvara novi gumb Quit s imenom "Quit"
    JButton Quit = new JButton("Quit");
    //varijabla koja signalizira je li korisnik poslao poruku ili nije
    //tj je li kliknuo Send ili ne, u početku inicijalizirana kao false.
    boolean OKPressed = false;
    //varijabla u koju će se spremati poruka koja se treba poslati
    //odnosno tekst unešen u UserText
    String poruka;
    //varijabla u koju će se spremati imena korisnika kojima se želi poslati poruka
    //odnosno tekst unešen u User
    String userpid;
    //varijabla koja će služiti za pohranjivanje koordinata pokazivača miša
    static Point mouseDownCompCoords=null;

    //konstrukor za GUI odnosno glavni prozor
    public MyFrame(){
        //funkcija koja spriječava mijenjanje veličine prozora
        setResizable(false);
        //funkcija koja postavlja veličinu prozora na 560×400
        setSize(560,400);

        setUndecorated(true);
        getRootPane().setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createBevelBorder(BevelBorder.RAISED),
            BorderFactory.createBevelBorder(BevelBorder.LOWERED)));

        //dohvaćamo "unutrašnjost" prozora, u njega možemo dodavati komponente
        Container cp=getContentPane();
        //kreiramo boje koje ćemo kasnije dodijeliti određenim komponentama
        Color c=new Color(64,192,203);
        Color d=new Color(104,222,151);
        Color e=new Color(95,209,141);
        Color g=new Color(79,172,116);
        Color f=new Color(204,204,204);
        //postavljamo boju cp-a na "c"
        cp.setBackground(c);
        //postavljamo layout na "FlowLayout.CENTER"
        //"FlowLayout" postavlja komponente u redoslijedu kojem su dodane
        //s lijeva na desno dok više nema mjesta, tada pređe u "novi red"
        //"CENTER" znači da će biti pozicionirane u sredini
        cp.setLayout(new FlowLayout(FlowLayout.CENTER));
        //kreira i dodaje JLabel "Chat History" u cp
        cp.add(new JLabel("Chat History"));
        //postavlja boju ChatBox-a na "d"
        ChatBox.setBackground(d);
        //kreira novi font "Calibri" podeblja ga i postavi ga na veličinu 14
        Font font=new Font("Calibri", Font.BOLD, 14);
        //postavljamo font ChatBox-a na "font"
        ChatBox.setFont(font);
        //dodaje tekst u ChatBox
        ChatBox.append("Pisite tekst u Chat Box u jednoj liniji jer ce svaki novi red bit zamijenen razmakom \n");
        //Onemogućavamo korisniku da editira ili dodaje tekst u ChatBox
        ChatBox.setEditable(false);
        //postavlja pozadinsku boju ScrollBar-ova ChatBox-a na "e"
        myChatHistory.getVerticalScrollBar().setBackground(e);
        myChatHistory.getHorizontalScrollBar().setBackground(e);
        //postavlja boju gumba koji nastaje kada tekst izađe iz
        //granica ChatBox-a na "g"
        myChatHistory.getVerticalScrollBar().setUI(new BasicScrollBarUI(){
            @Override
            protected void configureScrollBarColors(){
                this.thumbColor = g;
        }});
        myChatHistory.getHorizontalScrollBar().setUI(new BasicScrollBarUI(){
            @Override
            protected void configureScrollBarColors(){
                this.thumbColor = g;
        }});
        myChatHistory.getHorizontalScrollBar().getComponent(0).setBackground(e);
        myChatHistory.getHorizontalScrollBar().getComponent(1).setBackground(e);
        myChatHistory.getVerticalScrollBar().getComponent(0).setBackground(e);
        myChatHistory.getVerticalScrollBar().getComponent(1).setBackground(e);

        JTextField kut=new JTextField(1);
        kut.setBackground(e);
        kut.setBorder(BorderFactory.createEmptyBorder());
        kut.setEditable(false);
        myChatHistory.setCorner(JScrollPane.LOWER_RIGHT_CORNER,kut);


        //dodaje myChatHistory u cp
        cp.add(myChatHistory);
        //kreira i dodaje novi JLabel "Chat Box: " u cp
        cp.add(new JLabel("Chat Box : "));
        //na sličan način radimo sada dizajn "UserText"-a i njegovih "ScrollBar"-ova
        UserText.setBackground(d);

        myUserHistory.getVerticalScrollBar().setBackground(e);
        myUserHistory.getHorizontalScrollBar().setBackground(e);

        myUserHistory.getVerticalScrollBar().setUI(new BasicScrollBarUI(){
            @Override
            protected void configureScrollBarColors(){
                this.thumbColor = g;
        }});
        myUserHistory.getHorizontalScrollBar().setUI(new BasicScrollBarUI(){
            @Override
            protected void configureScrollBarColors(){
                this.thumbColor = g;
        }});

        myUserHistory.getHorizontalScrollBar().getComponent(0).setBackground(e);
        myUserHistory.getHorizontalScrollBar().getComponent(1).setBackground(e);
        myUserHistory.getVerticalScrollBar().getComponent(0).setBackground(e);
        myUserHistory.getVerticalScrollBar().getComponent(1).setBackground(e);
        //dodaje "myUserHistory" u cp
        cp.add(myUserHistory);
        //dodaje gumb "Send" u cp
        cp.add(Send);
        //postavljamo boju, font i tekst u "User"
        User.setBackground(d);
        User.setFont(font);
        User.setText("Unesite primatelje odvojene razmakom");
        //dodaje "User" u cp
        cp.add(User);
        //dodaje gumb "Quit" u cp
        cp.add(Quit);

        Send.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                poruka=(String)UserText.getText();
                poruka=poruka.replace("\n", " ");
                userpid=User.getText();
                OKPressed = true;
                UserText.setText(null);
            }
        });

        //Kreiramo događaj koji na klik gumba Quit prekine izvršavanje programa
        Quit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });
        //Kreiramo događaj koji na klick JTextField-a "User" izbriše postojeći tekst
        User.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                User.setText(null);
            }
        });

        //sljedeći kod omogućava da pomičemo prozor po ekranu kad god držimo i "vučemo" miš
        addMouseListener(new MouseAdapter(){
            @Override
            public void mouseReleased(MouseEvent e) {
                 mouseDownCompCoords = null;
            }
            @Override
            public void mousePressed(MouseEvent e) {
                mouseDownCompCoords = e.getPoint();
            }
        });
        addMouseMotionListener(new MouseAdapter(){
            @Override
            public void mouseDragged(MouseEvent e) {
                Point currCoords = e.getLocationOnScreen();
                setLocation(currCoords.x - mouseDownCompCoords.x, currCoords.y - mouseDownCompCoords.y);
            }
        });

        //naredba koja ako zatvorimo prozor, prekine izvršavanje programa
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //da bi se "MyFrame" prikazao na ekranu treba ga učiniti vidljivim
        //to čini sljedeća naredba
        setVisible(true);
    }
}
