import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import java.util.Date;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;


public class MyGame extends JFrame implements ActionListener
{
    JLabel heading,clockLabel;
    JPanel mainPanel;
    JButton []btns=new JButton[9];
    int gameChances[]={2,2,2,2,2,2,2,2,2};
    int activePlayer=0;
    int wps[] []={
        {0,1,2},
        {3,4,5},
        {6,7,8},
        {0,3,6},
        {1,4,7},
        {2,5,8},
        {0,4,8},
        {2,4,8},
    };
    int winner=2;
    boolean gameOver=false;
    public MyGame()
    {
        setTitle("Tic Tac Toe");
        setSize(400,400);
        ImageIcon icon=new ImageIcon("");
        setIconImage(icon.getImage());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createGUI();
        setVisible(true);
    }

    private void createGUI()
    {
        this.setLayout(new BorderLayout());
        heading=new JLabel("Tic Tac Toe");
        this.add(heading,BorderLayout.NORTH);
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        
        clockLabel=new JLabel("clock");
        this.add(clockLabel,BorderLayout.SOUTH);
        clockLabel.setHorizontalAlignment(SwingConstants.CENTER);

        Thread t=new Thread(){
            public void run(){
                try{
                    while(true)
                    {
                        String datetime=new Date().toString();
                        clockLabel.setText(datetime);
                        Thread.sleep(1000);
                    }

            } catch(Exception e){
                e.printStackTrace();
            }

            }
        };
        t.start();

       mainPanel=new JPanel();
        mainPanel.setLayout(new GridLayout(3,3));

        for(int i=1;i<=9;i++){
            JButton btn=new JButton();
            mainPanel.add(btn);
            btns[i-1]=btn;
            btn.addActionListener(this);
            btn.setName(String.valueOf(i-1));
        }
        this.add(mainPanel,BorderLayout.CENTER);
    }
    @Override
    public void actionPerformed(ActionEvent e)
    {
        JButton currentButton = (JButton)e.getSource();
        String nameStr = currentButton.getName();
        int name = Integer.parseInt(nameStr.trim());
        if(gameOver)
        {
            JOptionPane.showMessageDialog(this,"Game alredy over..");
            return;
        }




        if (gameChances[name]==2)
        {
            if(activePlayer==1)
            {
                currentButton.setIcon(new ImageIcon("images/images.png"));
                gameChances[name]=activePlayer;
                activePlayer=0;
            }else
            {
                currentButton.setIcon(new ImageIcon("images/download.png"));
                gameChances[name]=activePlayer;
                activePlayer=1;
            }
            //winner conditions
            for(int []temp:wps)
            {
                if((gameChances[temp[0]]==gameChances[temp[1]]&&gameChances[temp[1]]==gameChances[temp[2]]&& gameChances[temp[2]]!=2))
                {
                    winner=gameChances[temp[0]];
                    gameOver=true;
                    JOptionPane.showMessageDialog(null,"Player" + winner + "has won the game..");
                    int i=JOptionPane.showConfirmDialog(this,"do you want to play more?");
                    if(i==0)
                    {
                        this.setVisible(false);
                        new MyGame();
                    }else if(i==1)
                    {
                        System.exit(0);

                    }else
                    {

                    }
                    System.out.println(i);
                    break;
                }
            }
            //draw logic
            int c=0;
            for(int x:gameChances)
            {
                if(x==2)
                {
                    c++;
                    break;
                }
            }
            if(c==0 && gameOver==false)
            {
                JOptionPane.showMessageDialog(null,"Its draw..");

                int i=JOptionPane.showConfirmDialog(this,"Play more?");
                if(i==0)
                {
                    this.setVisible(false);
                    new MyGame();
                }else if(i==1){
                    System.exit(1);
                }else{

                }
                gameOver=true; 
            }


        }
        else
        {
            JOptionPane.showMessageDialog(this,"Postion already occupied...");
        }
    }

    public static void main(String[] args) 
    {
        new MyGame();
    }
}