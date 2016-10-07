import java.io.*; import java.util.*;

public class Chat extends Process {

    //stvaramo GUI
    static MyFrame t=new MyFrame();

    public Chat(Linker initComm) {
        super(initComm);
    }
    public synchronized void handleMsg(Msg m, int src, String tag){
        if (tag.equals("chat")) {
            //ispiši korisnika i poruku koju je poslao na ekran tj područje
            //u GUI-ju označeno sa Chat History primatelja
            t.ChatBox.append(src + ":" + m.getMessage() + "\n");
        }
    }
    public String getUserInput() throws Exception {
        //dok nije kliknut gumb "Send" spavaj, tj ne radi ništa
        while (t.OKPressed != true){Thread.sleep(500);}
        //kada je kliknut gumb dohvati vrijednost varijable poruka i spremi je u chatMsg
        //tj dohvati poslanu poruku
        String chatMsg=t.poruka;
        return chatMsg;
    }
    public IntLinkedList getDest(BufferedReader din) throws Exception {
        IntLinkedList destIds = new IntLinkedList(); //dest for msg
        //dok nije kliknut gumb "Send" spavaj, tj ne radi ništa
        while (t.OKPressed != true){Thread.sleep(500);}
        //kada je kliknut gumb dohvati vrijednost varijable userdpid, dodaj joj -1
        //i spremi je u st, tj dohvati listu primatelja poruke
        StringTokenizer st = new StringTokenizer(t.userpid +" -1");
        while (st.hasMoreTokens()) {
            int pid=Integer.parseInt(st.nextToken());
            if (pid == -1) break;
            else destIds.add(pid);
        }
        return destIds;
    }
    public static void main(String[] args) throws Exception {
        String baseName = "Chat";
        int myId = Integer.parseInt(args[0]);
        int numProc = Integer.parseInt(args[1]);
        Linker comm = null;
        comm = new CausalLinker(baseName, myId, numProc);
        Chat c = new Chat(comm);
        for (int i = 0; i < numProc; i++)
            if (i != myId) (new ListenerThread(i, c)).start();
        BufferedReader din = new BufferedReader(
        new InputStreamReader(System.in));
        while (true){
            System.out.println(c.getUserInput());
            String chatMsg = c.getUserInput();
            //probaj dohvatiti listu primatelja, postoji mogućnost da
            //korisnik ne unese primatelje ili unese nepostojeće
            try{
                IntLinkedList destIds=c.getDest(din);
                comm.multicast(destIds, "chat", chatMsg);
                //ispiši svoje ime i poslanu poruku na vlastiti ekran tj
                //područje u GUI-ju označeno sa Chat History
                t.ChatBox.append(myId + ": " + chatMsg +"\n");
                //signaliziraj da gumb Send više nije kliknut
                //tj da je obrađena poruka
                t.OKPressed=false;
            }
            //ako je "try" neuspješan ispiši poruku o grešci te signaliziraj
            //da gumb Send više nije kliknut tj da je obrađena poruka(neuspješno
            //doduše)
            catch(Exception e){
                t.ChatBox.append("Krivo ste unijeli primatelje, pokusajte ponovno \n");
                t.OKPressed=false;
            }
        }
    }
}



