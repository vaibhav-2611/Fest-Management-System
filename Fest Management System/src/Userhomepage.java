
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.*;
import javax.swing.*;
import net.proteanit.sql.DbUtils;
import java.awt.event.*;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.PriorityQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author vaibhav
 */

public class Userhomepage extends javax.swing.JFrame {
    /**
     * Creates new form Userhomepage
     */
    Connection conn=null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    private String user=null;
    public Userhomepage() {
        initComponents();
        conn=javaconnect.ConnecrDb();
        Update_table_avail(1);
        System.out.println("yes");
        room_table();
//        call_notifications();
        show_time();
    }
    public Userhomepage(String s) {
        initComponents();
        conn=javaconnect.ConnecrDb();
        Update_table_avail(1);
        System.out.println("yes");
        user=s;
        room_table();
//        call_notifications();
        show_time();
    }
    private String givemecorrectstring(String s){
        System.out.println(s.substring(0,4));
        if((s.substring(0,4)).equals("null"))
        {               s=s.substring(4);
        }else
        {               s=s;
        } 
        return s;
    }
    private void Update_table_avail(int x){
        String sql="select Eventid, Eventname from Day"+x+"events";
        try{
            pst= conn.prepareStatement(sql);            
            rs=pst.executeQuery();
            avail_table.setModel(DbUtils.resultSetToTableModel(rs));
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
        finally{
            try{
                rs.close();
                pst.close();
            }
            catch(Exception e){
                         JOptionPane.showMessageDialog(null,e);  
            }
        }
    }
    private void call_notifications(){ // slit by ;
    try{
        rs.close();
        pst.close();
        String sql="select Notifications from Logindata where Username=?";
        pst= conn.prepareStatement(sql);
        pst.setString(1, user);
        rs=pst.executeQuery();
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        String tosplitt=null;
        while(rs.next()){
            tosplitt=rs.getString("Notifications");
        }
        if(tosplitt==null){
            model.addElement("No Notifications.");
            Noti.setModel(model);
        }else{  
            try{
                String[] r=tosplitt.split(";");
                for(String a:r){
                    model.addElement(a);
                }
                Noti.setModel(model);
            }catch(Exception e){
                JOptionPane.showMessageDialog(null,e);
            }
        }
    }catch(SQLException | HeadlessException e){
        JOptionPane.showMessageDialog(null,e);
    }finally{
        try{
            rs.close();
            pst.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
    }   
}
    private void room_table(){
        try{
            rs.close();
            pst.close();
            String sql="select HallID,Tarrif,Available_Rooms from HallList order by Tarrif";
            pst= conn.prepareStatement(sql);            
            rs=pst.executeQuery();
            room_book.setModel(DbUtils.resultSetToTableModel(rs));
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }finally{
            try{
                rs.close();
                pst.close();
            }
            catch(SQLException | HeadlessException e){
                JOptionPane.showMessageDialog(null,e);
            }
        }
    }                                              //checked
    private int giveamount(){
        int a= Integer.parseInt(stay_day.getSelectedItem().toString());
        int b= Integer.parseInt(stay_room.getSelectedItem().toString());
        int avai=0;
        String s= room_1.getText();
        int cost=0;
        int tfare=0;
        try{
            String sql="select HallID,Tarrif,Available_Rooms from HallList where HallID=?";
                pst= conn.prepareStatement(sql);            
                pst.setString(1,s);
                rs=pst.executeQuery();
                if(rs.next()){
                    cost=Integer.parseInt(rs.getString("Tarrif"));
                    avai=Integer.parseInt(rs.getString("Available_Rooms"));
                    if(avai>=b){
                        tfare= cost*(a*b);
                        fare.setText(""+tfare);
                    }
                    try{
                        rs.close();
                        pst.close();
                    }
                    catch(SQLException | HeadlessException e){
                        JOptionPane.showMessageDialog(null,e);
                    }
                    return tfare;
                }
            }catch(SQLException | NumberFormatException e){
                JOptionPane.showMessageDialog(null,"Enter Correct Hall ID");
            }
            finally{
                try{
                    rs.close();
                    pst.close();
                }
                catch(SQLException | HeadlessException e){
                    JOptionPane.showMessageDialog(null,e);
                }
            }
        return tfare;
    }                                               //checked
    private String update_rooms(String s,int r) throws SQLException{
        rs.close();
        pst.close();
        String Hallid=s;
        String HallName=null;
        String Total_Rooms=null;
        String Available_Rooms=null;
        String Tarrif=null;
        String rftn=null;
        try{
            String sql="select * from HallList where HallID = ?";
            pst= conn.prepareStatement(sql);
            pst.setString(1,Hallid);
            rs=pst.executeQuery();
            if(rs.next()){
                HallName=rs.getString("HallName");
                Total_Rooms=rs.getString("Total_Rooms");
                Available_Rooms=rs.getString("Available_Rooms");
                Tarrif=rs.getString("Tarrif");
                rftn=rs.getString("Room_No_filled");
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                JOptionPane.showMessageDialog(null,e);
            }
        }
        try{
            String sql="delete from HallList where HallID=?";
            pst= conn.prepareStatement(sql);
            pst.setString(1, Hallid);
            pst.execute();
            pst.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                JOptionPane.showMessageDialog(null,e);
            }
        }
        String roomlist="";   
        try{
            String sql="Insert into HallList (HallID,HallName,Total_Rooms,Available_Rooms,Tarrif,Room_No_filled) values (?,?,?,?,?,?)";
            pst= conn.prepareStatement(sql);
            pst.setString(1, Hallid);
            pst.setString(2, HallName);
            pst.setString(3, Total_Rooms);
            int k=Integer.parseInt(Available_Rooms)-r;
            String test=Integer.toString(k);
            pst.setString(4, test);
            pst.setString(5, Tarrif);
            roomlist=Integer.toString(Integer.parseInt(rftn)+1);
            int j=Integer.parseInt(rftn)+1+1;
            for(int i=1;i<r;i++){
                roomlist+=","+j;
                j++;
            }
            j--;
            pst.setString(6, Integer.toString(j));
            pst.execute();
//            JOptionPane.showMessageDialog(null,"Added HuRRay!!");
        }catch(SQLException | NumberFormatException | HeadlessException e){
            JOptionPane.showMessageDialog(null,e);
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                JOptionPane.showMessageDialog(null,e);
            }
        }
        return roomlist;
}        //checked
    private String get_hall_name(String s){
        String ans="";
        try{

            String sql="select HallName from HallList where HallID=?";
            pst= conn.prepareStatement(sql);
            pst.setString(1,s);
            rs=pst.executeQuery();
            if(rs.next()){
                ans=rs.getString("HallName");
            }

        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                        JOptionPane.showMessageDialog(null,e);

            }
        }
        return ans;
    }
    private void update_notifications(String addnoti){
        String Username=user;
        String Password=null;
        String Name=null;
        String Address=null;
        String Email=null;
        String Phoneno=null;
        String Waletmoney=null;
        String Activestatus=null;
        String Designation=null;
        String Institute=null;
        String City=null;
        String State=null;
        String Moneyd=null;
        String Hall=null;
        String RoomNo=null;
        String Notifications=null;
        try{
            String sql="select * from Logindata where Username = ?";
            pst= conn.prepareStatement(sql);
            pst.setString(1,Username);
            rs=pst.executeQuery();
            if(rs.next()){
                Password=rs.getString("Password");
                Name=rs.getString("Name");
                Address=rs.getString("Address");
                Email=rs.getString("Email");
                Phoneno=rs.getString("Phoneno");
                Waletmoney=rs.getString("Waletmoney");
                Activestatus=rs.getString("Activestatus");
                Designation=rs.getString("Designation");
                Institute=rs.getString("InstituteName");
                City=rs.getString("City");
                State=rs.getString("State");
                Moneyd=rs.getString("Moneydemand");
                RoomNo=rs.getString("RoomNo");
                Hall=rs.getString("HallName");
                Notifications=rs.getString("Notifications");
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                JOptionPane.showMessageDialog(null,e);
            }
        }
        try{
            String sql="delete from Logindata where Username=?";
                pst= conn.prepareStatement(sql);
                pst.setString(1, Username);
                pst.execute();
            }catch(SQLException | HeadlessException e){
                JOptionPane.showMessageDialog(null,e);
            }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){    
            }
        }
//to change        
        try{
            String sql="Insert into Logindata (Username,Password,Name,Address,Email,Phoneno,Waletmoney,Activestatus,Designation,InstituteName,City,State,Moneydemand,HallName,RoomNo,Notifications) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            pst= conn.prepareStatement(sql);
            pst.setString(1, Username);
            pst.setString(2, Password);
            pst.setString(3, Name);
            pst.setString(4, Address);
            pst.setString(5, Email);
            pst.setString(6, Phoneno);
            pst.setString(7, Waletmoney);
            pst.setString(8, Activestatus);
            pst.setString(9, Designation);
            pst.setString(10, Institute);
            pst.setString(11, City);
            pst.setString(12, State);
            pst.setString(13, Moneyd);
            pst.setString(14, Hall);
            pst.setString(15, RoomNo);
            Notifications+=addnoti;
            Notifications=givemecorrectstring(Notifications);
            pst.setString(16, Notifications);
            pst.execute();
        }catch(SQLException | HeadlessException e){
            JOptionPane.showMessageDialog(null,e);
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                JOptionPane.showMessageDialog(null,e);
            }
        }
    }                      //checked
    private void show_time(){
        Thread clock=new Thread(){
            public void run(){
                for(;;){
                        Calendar c=new GregorianCalendar();
                        int month=c.get(Calendar.MONTH);
                        int year=c.get(Calendar.YEAR);
                        int day=c.get(Calendar.DAY_OF_MONTH);
                        dates.setText("DATE:"+day+"/"+(month+1)+"/"+year);
                        int second=c.get(Calendar.SECOND);
                        int minute=c.get(Calendar.MINUTE);
                        int hour=c.get(Calendar.HOUR);
                        times.setText("TIME:"+hour+":"+minute+":"+second);

                    try{
                        sleep(1000);
                    }catch(InterruptedException e){
                        JOptionPane.showMessageDialog(null,"TimEEE ErroRRR!!");

                    }
                }
            }
        };
        clock.start();
    }
    private int update_event(String s,int day,int count){
        String Eventid=s;
        String Starttime=null;
        String Endtime=null;
        String Venue=null;
        String Description=null;
        String Seats=null;
        String Eventname=null;
        String Availableseats=null;
        int flag=1;
        try{
            pst.close();
            rs.close();
            String sql="select * from Day"+day+"events where Eventid = ?";
            pst= conn.prepareStatement(sql);
            pst.setString(1,Eventid);
            rs=pst.executeQuery();   
            if(rs.next()){
                Starttime=rs.getString("Starttime");
                Endtime=rs.getString("Endtime");
                Venue=rs.getString("Venue");
                Description=rs.getString("Description");
                Seats=rs.getString("Seats");
                Eventname=rs.getString("Eventname");
                Availableseats=rs.getString("Availableseats");
                flag=1;
            }
            else{
                flag=0;
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }finally{
            try{
                rs.close();
                pst.close();
                if(flag==0)
                    return 0; //not in this day
                else if(Integer.parseInt(Availableseats)>=count)
                    { }
                else
                {//  JOptionPane.showMessageDialog(null,"Oops!! "+count +" Seat not Available");  
                    return -1; //in this day but not enough seats
                }
            }catch(SQLException | NumberFormatException e){ 
                JOptionPane.showMessageDialog(null,"4232323XXXXXay!! day="+day);
            }
        }
        try{
            String sql="delete from Day"+day+"events where Eventid=?";
            pst= conn.prepareStatement(sql);
            pst.setString(1, Eventid);
            pst.execute();
            pst.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){    
                JOptionPane.showMessageDialog(null,e);
            }
        }
//to change        
        try{
            String sql="Insert into Day"+day+"events (Eventid,Starttime,Endtime,Venue,Description,Seats,Eventname,Availableseats) values (?,?,?,?,?,?,?,?)";
            pst= conn.prepareStatement(sql);
            pst.setString(1, Eventid);
            pst.setString(2, Starttime);
            pst.setString(3, Endtime);
            pst.setString(4, Venue);
            pst.setString(5, Description);
            pst.setString(6, Seats);
            pst.setString(7, Eventname);
            pst.setString(8, Integer.toString(Integer.parseInt(Availableseats)-count));
            pst.execute();
//            JOptionPane.showMessageDialog(null,"Added HuRRay!!");
        }catch(SQLException | NumberFormatException | HeadlessException e){
            JOptionPane.showMessageDialog(null,e);
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                JOptionPane.showMessageDialog(null,e);
            }
        }
        return 2;
}
    private void update(int wm,String user,String hname,String roomlist){   //wm=final walet money
        String Username=user;
        String Password=null;
        String Name=null;
        String Address=null;
        String Email=null;
        String Phoneno=null;
        String Activestatus=null;
        String Designation=null;
        String Institute=null;
        String City=null;
        String State=null;
        String Moneyd=null;
        String Hall=null;
        String RoomNo=null;
        String Notifications=null;
        try{
            String sql="select * from Logindata where Username = ?";
            pst= conn.prepareStatement(sql);
            pst.setString(1,Username);
            rs=pst.executeQuery();
            if(rs.next()){
                Password=rs.getString("Password");
                Name=rs.getString("Name");
                Address=rs.getString("Address");
                Email=rs.getString("Email");
                Phoneno=rs.getString("Phoneno");
                Activestatus=rs.getString("Activestatus");
                Designation=rs.getString("Designation");
                Institute=rs.getString("InstituteName");
                City=rs.getString("City");
                State=rs.getString("State");
                Moneyd=rs.getString("Moneydemand");
                RoomNo=rs.getString("RoomNo");
                Hall=rs.getString("HallName");
                Notifications=rs.getString("Notifications");
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                JOptionPane.showMessageDialog(null,e);
            }
        }
        try{    
            String sql="delete from Logindata where Username=?";
            pst= conn.prepareStatement(sql);
            pst.setString(1, Username);
            pst.execute();
//            JOptionPane.showMessageDialog(null,"DeLeTeD HuRRay!!");
        }catch(SQLException | HeadlessException e){
            JOptionPane.showMessageDialog(null,e);
        }finally{
            try{rs.close();
                pst.close();
            }catch(Exception e){     JOptionPane.showMessageDialog(null,e);
            }
        }
//to change
        try{
            String sql="Insert into Logindata (Username,Password,Name,Address,Email,Phoneno,Waletmoney,Activestatus,Designation,InstituteName,City,State,Moneydemand,HallName,RoomNo,Notifications) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            pst= conn.prepareStatement(sql);
            pst.setString(1, Username);
            pst.setString(2, Password);
            pst.setString(3, Name);
            pst.setString(4, Address);
            pst.setString(5, Email);
            pst.setString(6, Phoneno);
            pst.setString(7, Integer.toString(wm));
            pst.setString(8, Activestatus);
            pst.setString(9, Designation);
            pst.setString(10, Institute);
            pst.setString(11, City);
            pst.setString(12, State);
            pst.setString(13, Moneyd);
            Hall=givemecorrectstring(Hall+" "+hname);
            pst.setString(14, Hall);
            RoomNo=givemecorrectstring(RoomNo+" "+roomlist);
            pst.setString(15, RoomNo);
            pst.setString(16, Notifications);
            pst.execute();
            pst.close();
//            JOptionPane.showMessageDialog(null,"Added HuRRay!!");
        }catch(SQLException | HeadlessException e){
            JOptionPane.showMessageDialog(null,e);
        }finally{
            try{
                rs.close();
                pst.close();
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null,e);  
            }
        }
    }   //checked
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel8 = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        times = new javax.swing.JLabel();
        dates = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        tabs = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        path = new javax.swing.JButton();
        jLabel25 = new javax.swing.JLabel();
        destination = new javax.swing.JComboBox<>();
        source = new javax.swing.JComboBox<>();
        jLabel24 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        boxbox = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        Noti = new javax.swing.JComboBox<>();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        f_id = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        f_seats = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        fare = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        stay_day = new javax.swing.JComboBox<>();
        stay_room = new javax.swing.JComboBox<>();
        jLabel19 = new javax.swing.JLabel();
        room_1 = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        room_book = new javax.swing.JTable();
        jLabel21 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        total_seats = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        avai_seats = new javax.swing.JTextField();
        avail_progress = new javax.swing.JProgressBar();
        jButton6 = new javax.swing.JButton();
        avail_event_id = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        box = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        avail_table = new javax.swing.JTable();
        jLabel23 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        p_wallet = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        add_money = new javax.swing.JTextField();
        jPanel11 = new javax.swing.JPanel();
        p_name = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        p_city = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        p_state = new javax.swing.JTextField();
        p_address = new javax.swing.JTextField();
        p_institute = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        p_mobile = new javax.swing.JTextField();
        p_email = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(254, 162, 59));

        jLabel8.setFont(new java.awt.Font("Utopia", 3, 36)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(77, 0, 255));
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/balloons.png"))); // NOI18N
        jLabel8.setText("SPRING FEST, IIT KHARAGPUR");

        jButton7.setFont(new java.awt.Font("Utopia", 3, 24)); // NOI18N
        jButton7.setForeground(new java.awt.Color(255, 0, 0));
        jButton7.setText("LogOut");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        times.setText("jLabel24");

        dates.setText("jLabel25");

        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        tabs.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabsMouseClicked(evt);
            }
        });
        tabs.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tabsKeyPressed(evt);
            }
        });

        jLabel27.setFont(new java.awt.Font("Utopia", 3, 36)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 0, 58));
        jLabel27.setText("Contact : 9876543210");

        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Utopia", 0, 12))); // NOI18N

        jLabel26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/spring-fest-600x360.jpg"))); // NOI18N

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel26)
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel26)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(118, 118, 118)
                        .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(248, 248, 248)
                        .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(118, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51)
                .addComponent(jLabel27)
                .addGap(43, 43, 43))
        );

        tabs.addTab("WELCOME", jPanel4);

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Shortest Path", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Utopia", 3, 36), new java.awt.Color(255, 0, 0))); // NOI18N

        path.setFont(new java.awt.Font("Utopia", 3, 24)); // NOI18N
        path.setForeground(new java.awt.Color(66, 143, 33));
        path.setText("Find Path");
        path.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pathActionPerformed(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("Utopia", 3, 18)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(80, 102, 33));
        jLabel25.setText("Source");

        destination.setFont(new java.awt.Font("Utopia", 2, 18)); // NOI18N
        destination.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "LBS", "MMM", "LLR", "AZAD", "NEHRU", "PATEL", "BC ROY HOSPITAL", "BRH", "PAN LOOP", "DIRECTOR's HOUSE", "NALANDA COMPLEX", "VIKRAMSHILA", "MUSEUM", "VGSOM", "ARENA", "SBI BANK", "TIKKA", "SN", "MT", "GYMKHANA", "BASKETBALL COURT", "TENIS COURT", "GYNAN GHOSH STADIUM", "MAHATAMA GHANDI STADIUM", "TATA STEEL COMPLEX", "RP", "RK", "MS", "TECH MARKET", "MAIN BUILDING" }));

        source.setFont(new java.awt.Font("Utopia", 2, 18)); // NOI18N
        source.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "LBS", "MMM", "LLR", "AZAD", "NEHRU", "PATEL", "BC ROY HOSPITAL", "BRH", "PAN LOOP", "DIRECTOR's HOUSE", "NALANDA COMPLEX", "VIKRAMSHILA", "MUSEUM", "VGSOM", "ARENA", "SBI BANK", "TIKKA", "SN", "MT", "GYMKHANA", "BASKETBALL COURT", "TENIS COURT", "GYNAN GHOSH STADIUM", "MAHATAMA GHANDI STADIUM", "TATA STEEL COMPLEX", "RP", "RK", "MS", "TECH MARKET", "MAIN BUILDING" }));

        jLabel24.setFont(new java.awt.Font("Utopia", 3, 18)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(80, 102, 33));
        jLabel24.setText("Destination");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel25)
                    .addComponent(jLabel24))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(path, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(source, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(destination, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(source, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(destination, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24))
                .addGap(18, 18, 18)
                .addComponent(path)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton4.setFont(new java.awt.Font("Utopia", 3, 24)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 0, 0));
        jButton4.setText("IIT KGP Map");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        boxbox.setEditable(false);
        boxbox.setBackground(new java.awt.Color(251, 249, 136));
        boxbox.setFont(new java.awt.Font("Bitstream Charter", 3, 14)); // NOI18N
        boxbox.setForeground(new java.awt.Color(255, 0, 0));
        boxbox.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 181, 0)), javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Utopia", 3, 18), new java.awt.Color(0, 86, 173)))); // NOI18N
        jScrollPane3.setViewportView(boxbox);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(57, 57, 57)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(78, 78, 78)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 597, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(72, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(86, 86, 86)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(123, 123, 123))
        );

        tabs.addTab("Maps", jPanel5);

        Noti.setBackground(new java.awt.Color(255, 233, 0));
        Noti.setForeground(new java.awt.Color(255, 19, 0));
        Noti.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                NotiMouseClicked(evt);
            }
        });
        Noti.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NotiActionPerformed(evt);
            }
        });

        jLabel28.setFont(new java.awt.Font("Utopia", 3, 36)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(216, 11, 142));
        jLabel28.setText("Check Your Activity");

        jLabel29.setFont(new java.awt.Font("Utopia", 3, 36)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(216, 11, 142));
        jLabel29.setText("Notifications");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Noti, 0, 840, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel28)
                .addGap(256, 256, 256))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel29)
                .addGap(311, 311, 311))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(jLabel28)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel29)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 120, Short.MAX_VALUE)
                .addComponent(Noti, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(222, 222, 222))
        );

        tabs.addTab("Notifications", jPanel6);

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Booking Form", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lato Semibold", 3, 24), new java.awt.Color(255, 0, 0))); // NOI18N

        jLabel11.setFont(new java.awt.Font("Lato Semibold", 2, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(196, 14, 14));
        jLabel11.setText("Enter Event ID");

        jLabel12.setFont(new java.awt.Font("Lato Semibold", 2, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(196, 14, 14));
        jLabel12.setText("Number of Seats");

        jButton5.setFont(new java.awt.Font("Lato Semibold", 3, 18)); // NOI18N
        jButton5.setText("Confirm Booking");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        f_seats.setText("1");
        f_seats.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                f_seatsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(37, 37, 37)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(f_seats)
                            .addComponent(f_id, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jButton5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(f_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(f_seats, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addComponent(jButton5)
                .addContainerGap())
        );

        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/purchase-books.png"))); // NOI18N

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGap(78, 78, 78)
                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(92, 92, 92)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(165, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(103, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(206, 206, 206))
        );

        tabs.addTab("Book Seat", jPanel8);

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Booking Form", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lato Semibold", 3, 18), new java.awt.Color(255, 0, 0))); // NOI18N

        fare.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fareActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Lato Semibold", 3, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(1, 1, 1));
        jLabel10.setText("Number of Rooms");

        jLabel9.setFont(new java.awt.Font("Lato Semibold", 3, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(1, 1, 1));
        jLabel9.setText("Number of days of stay");

        jButton2.setFont(new java.awt.Font("Lato Semibold", 3, 18)); // NOI18N
        jButton2.setForeground(new java.awt.Color(1, 1, 1));
        jButton2.setText("Calculate Fare");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Lato Semibold", 3, 18)); // NOI18N
        jButton3.setForeground(new java.awt.Color(1, 1, 1));
        jButton3.setText("Confirm Booking");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        stay_day.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4" }));

        stay_room.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));

        jLabel19.setFont(new java.awt.Font("Utopia", 3, 18)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(1, 1, 1));
        jLabel19.setText("Enter Hall ID");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel9)
                            .addComponent(jButton2)
                            .addComponent(jLabel10)
                            .addComponent(jLabel19))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(stay_day, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(stay_room, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(fare, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 30, Short.MAX_VALUE))
                            .addComponent(room_1))))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(room_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(stay_day, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(stay_room, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fare, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addGap(37, 37, 37)
                .addComponent(jButton3)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        room_book.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(room_book);

        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rooms-available.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(288, 288, 288)
                        .addComponent(jLabel21)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(112, Short.MAX_VALUE))
        );

        tabs.addTab("Book Room", jPanel2);

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Enquiry", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lato Semibold", 3, 24), new java.awt.Color(253, 106, 67))); // NOI18N

        total_seats.setEditable(false);

        jLabel15.setFont(new java.awt.Font("Utopia", 3, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(1, 1, 1));
        jLabel15.setText("Enter Event ID");

        avai_seats.setEditable(false);

        avail_progress.setFont(new java.awt.Font("Utopia", 3, 18)); // NOI18N
        avail_progress.setStringPainted(true);

        jButton6.setFont(new java.awt.Font("Utopia", 3, 18)); // NOI18N
        jButton6.setForeground(new java.awt.Color(1, 1, 1));
        jButton6.setText("Get Availability");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Utopia", 3, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(1, 1, 1));
        jLabel16.setText("Available Seats");

        jLabel13.setFont(new java.awt.Font("Utopia", 3, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(1, 1, 1));
        jLabel13.setText("Total Seats");

        jLabel18.setFont(new java.awt.Font("Utopia", 3, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(1, 1, 1));
        jLabel18.setText("Filled:");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16)
                            .addComponent(jLabel13)
                            .addComponent(jLabel18))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(total_seats)
                            .addComponent(avai_seats, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(avail_event_id, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addComponent(jButton6)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addComponent(avail_progress, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(avail_event_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addGap(18, 18, 18)
                .addComponent(jButton6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(total_seats, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(avai_seats, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel16)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(avail_progress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel17.setFont(new java.awt.Font("Utopia", 3, 24)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(52, 0, 255));
        jLabel17.setText("Day:");

        box.setFont(new java.awt.Font("Utopia", 2, 18)); // NOI18N
        box.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Day1", "Day2", "Day3" }));
        box.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                boxItemStateChanged(evt);
            }
        });

        avail_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(avail_table);

        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/checklist.png"))); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel17)
                        .addGap(36, 36, 36)
                        .addComponent(box, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(85, 85, 85)
                        .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 441, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(23, Short.MAX_VALUE))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(90, 90, 90)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(90, 90, 90)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(box, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(256, 256, 256))
        );

        tabs.addTab("Seat Availability", jPanel3);

        jLabel3.setFont(new java.awt.Font("Utopia", 3, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(42, 134, 21));
        jLabel3.setText("Wallet Money");

        p_wallet.setEditable(false);
        p_wallet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                p_walletActionPerformed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Utopia", 3, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 0, 0));
        jButton1.setText("Add Money");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Personal Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lato Semibold", 3, 18), new java.awt.Color(18, 179, 4))); // NOI18N
        jPanel11.setForeground(new java.awt.Color(38, 250, 7));

        p_name.setEditable(false);
        p_name.setFont(new java.awt.Font("Utopia", 2, 18)); // NOI18N

        jLabel14.setFont(new java.awt.Font("Utopia", 3, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(45, 1, 225));
        jLabel14.setText("Institute Name");

        jLabel1.setFont(new java.awt.Font("Utopia", 3, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(45, 1, 225));
        jLabel1.setText("Name");

        jLabel2.setFont(new java.awt.Font("Utopia", 3, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(45, 1, 225));
        jLabel2.setText("Address");

        p_city.setEditable(false);
        p_city.setFont(new java.awt.Font("Utopia", 2, 18)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Utopia", 3, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(45, 1, 225));
        jLabel7.setText("E-Mail");

        jLabel6.setFont(new java.awt.Font("Utopia", 3, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(45, 1, 225));
        jLabel6.setText("Mobile No.");

        p_state.setEditable(false);
        p_state.setFont(new java.awt.Font("Utopia", 2, 18)); // NOI18N
        p_state.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                p_stateActionPerformed(evt);
            }
        });

        p_address.setEditable(false);
        p_address.setFont(new java.awt.Font("Utopia", 2, 18)); // NOI18N

        p_institute.setEditable(false);
        p_institute.setFont(new java.awt.Font("Utopia", 2, 18)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Utopia", 3, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(45, 1, 225));
        jLabel5.setText("State");

        p_mobile.setEditable(false);
        p_mobile.setFont(new java.awt.Font("Utopia", 2, 18)); // NOI18N
        p_mobile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                p_mobileActionPerformed(evt);
            }
        });

        p_email.setEditable(false);
        p_email.setFont(new java.awt.Font("Utopia", 2, 18)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Utopia", 3, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(45, 1, 225));
        jLabel4.setText("City");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel2)
                        .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING))
                    .addComponent(jLabel5)
                    .addComponent(jLabel7)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(p_mobile)
                    .addComponent(p_state)
                    .addComponent(p_address)
                    .addComponent(p_name)
                    .addComponent(p_city)
                    .addComponent(p_email, javax.swing.GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)
                    .addComponent(p_institute))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(p_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(p_address, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(p_city, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(p_state, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(p_mobile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(p_email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(2, 2, 2)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(p_institute, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addContainerGap())
        );

        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/profile.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(64, 64, 64)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(add_money, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(p_wallet, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(add_money, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(p_wallet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(192, Short.MAX_VALUE))
        );

        tabs.addTab("Profile", jPanel1);

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabs, javax.swing.GroupLayout.PREFERRED_SIZE, 872, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabs, javax.swing.GroupLayout.PREFERRED_SIZE, 568, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(dates)
                        .addGap(49, 49, 49)
                        .addComponent(times, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 610, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(48, 48, 48)
                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jButton7))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dates)
                    .addComponent(times))
                .addGap(37, 37, 37)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(988, 812));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void p_walletActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_p_walletActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_p_walletActionPerformed

    private void p_stateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_p_stateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_p_stateActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        String ID = avail_event_id.getText();            
        try{
            String sql="select * from Day1events where Eventid=?";
            pst= conn.prepareStatement(sql);
            pst.setString(1,ID);
            rs=pst.executeQuery();
            if(rs.next()){
                avai_seats.setText(rs.getString("Availableseats"));
                total_seats.setText(rs.getString("Seats"));
                int i=Integer.parseInt(rs.getString("Seats"));
                int j=Integer.parseInt(rs.getString("Availableseats"));
                if(i!=0){
                    float filled=(((float)(i-j))/i)*100;
                    avail_progress.setValue((int)(filled));
                }else{
                    avail_progress.setValue(0);
                }
            }
            else{
                sql="select * from Day2events where Eventid=?";
                pst= conn.prepareStatement(sql);
                pst.setString(1,ID);
                rs=pst.executeQuery();
                if(rs.next()){
                    avai_seats.setText(rs.getString("Availableseats"));
                    total_seats.setText(rs.getString("Seats"));
                    int i=Integer.parseInt(rs.getString("Seats"));
                    int j=Integer.parseInt(rs.getString("Availableseats"));
                    if(i!=0){
                        float filled=(((float)(i-j))/i)*100;
                        avail_progress.setValue((int)(filled));
                    }else{
                        avail_progress.setValue(0);
                    }
                    
                }
               else{ 
                    sql="select * from Day3events where Eventid=?";
                    pst= conn.prepareStatement(sql);
                    pst.setString(1,ID);
                    rs=pst.executeQuery();
                    if(rs.next()){
                    avai_seats.setText(rs.getString("Availableseats"));
                    total_seats.setText(rs.getString("Seats"));               
                    int i=Integer.parseInt(rs.getString("Seats"));
                    int j=Integer.parseInt(rs.getString("Availableseats"));
                    if(i!=0){
                        float filled=(((float)(i-j))/i)*100;
                        avail_progress.setValue((int)(filled));
                    }else{
                        avail_progress.setValue(0);
                    }
                    
                    }
                }
            }
        }catch(SQLException | NumberFormatException e){
            JOptionPane.showMessageDialog(null,e);
        }finally{
        try{
            rs.close();
            pst.close();
        }
        catch(Exception e){
            }
            try {
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(Userhomepage.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(Userhomepage.class.getName()).log(Level.SEVERE, null, ex);
            }
    }//GEN-LAST:event_jButton6ActionPerformed
}
    public void cclose(){
        WindowEvent winClosingEvent = new WindowEvent(this,WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(winClosingEvent);
    }
    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
                    JOptionPane.showMessageDialog(null,"Successful LogOut!!");

        cclose();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void tabsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabsKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tabsKeyPressed

    private void p_mobileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_p_mobileActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_p_mobileActionPerformed

    private void tabsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabsMouseClicked
        System.out.println("pressed");
        System.out.println(tabs.getSelectedIndex());
        switch (tabs.getSelectedIndex()) {
            case 6:
                try{
                    pst.close();
                    rs.close();
                    String sql="select * from Logindata where Username=?";
                    pst= conn.prepareStatement(sql);
                    pst.setString(1,user);
                    rs=pst.executeQuery();
                    if(rs.next()){
                        p_name.setText(rs.getString("Name"));
                        p_address.setText(rs.getString("Address"));
                        p_city.setText(rs.getString("City"));
                        p_state.setText(rs.getString("State"));
                        p_wallet.setText(rs.getString("Waletmoney"));
                        p_institute.setText(rs.getString("InstituteName"));
                        p_email.setText(rs.getString("Email"));
                        p_mobile.setText(rs.getString("Phoneno"));
                    }
                }catch(Exception e){
                    System.out.println("ERROR");
                }finally{
                    try{
                        rs.close();
                        pst.close();
                    }
                    catch(Exception e){
                        JOptionPane.showMessageDialog(null,e);
                    }
                }   break;
            case 2:
                call_notifications();
                break;
            default:
                break;
        }
    }//GEN-LAST:event_tabsMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
//        String Username=null;
        String a=add_money.getText();
        String Username=user;
        String Password=null;
        String Name=null;
        String Address=null;
        String Email=null;
        String Phoneno=null;
        String Waletmoney=null;
        String Activestatus=null;
        String Designation=null;
        String Institute=null;
        String City=null;
        String State=null;
        String Moneyd=null;
        String Hall=null;
        String RoomNo=null;
        String Notifications=null;
        try{
//            JOptionPane.showMessageDialog(null,user);
            String sql="select * from Logindata where Username = ?";
            pst= conn.prepareStatement(sql);
            pst.setString(1,user);
            rs=pst.executeQuery();
            if(rs.next()){
                Password=rs.getString("Password");
                Name=rs.getString("Name");
                Address=rs.getString("Address");
                Email=rs.getString("Email");
                Phoneno=rs.getString("Phoneno");
                Waletmoney=rs.getString("Waletmoney");
                Activestatus=rs.getString("Activestatus");
                Designation=rs.getString("Designation");
                Institute=rs.getString("InstituteName");
                City=rs.getString("City");
                State=rs.getString("State");
                Moneyd=rs.getString("Moneydemand");
                RoomNo=rs.getString("RoomNo");
                Hall=rs.getString("HallName");
                Notifications=rs.getString("Notifications");
                // System.out.println("money:");
                // System.out.println(Moneyd);
                // System.out.println("name "+Password);
            }
        }
            catch(HeadlessException | SQLException e){
                    JOptionPane.showMessageDialog(null,e);
            }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                            JOptionPane.showMessageDialog(null,e);

            }
            }
        try{
                String sql="delete from Logindata where Username=?";
                pst= conn.prepareStatement(sql);
                pst.setString(1, Username);
                pst.execute();
                pst.close();
//                JOptionPane.showMessageDialog(null,"DeLeTeD HuRRay!!");
            }catch(SQLException | HeadlessException e){
                JOptionPane.showMessageDialog(null,e);
            }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){        
                        JOptionPane.showMessageDialog(null,e);
            }
        }
        try{
            String sql="Insert into Logindata (Username,Password,Name,Address,Email,Phoneno,Waletmoney,Activestatus,Designation,InstituteName,City,State,Moneydemand,HallName,RoomNo,Notifications) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            pst= conn.prepareStatement(sql);
            pst.setString(1, Username);
            pst.setString(2, Password);
            pst.setString(3, Name);
            pst.setString(4, Address);
            pst.setString(5, Email);
            pst.setString(6, Phoneno);
            pst.setString(7, Waletmoney);
            pst.setString(8, Activestatus);
            pst.setString(9, Designation);
            pst.setString(10, Institute);
            pst.setString(11, City);
            pst.setString(12, State);
            pst.setString(13, Integer.toString(Integer.parseInt(Moneyd)+Integer.parseInt(a)));
            pst.setString(14, Hall);
            pst.setString(15, RoomNo);
            pst.setString(16, Notifications);
            pst.execute();
            pst.close();
                        Calendar c=new GregorianCalendar();
                        int month=c.get(Calendar.MONTH);
                        int year=c.get(Calendar.YEAR);
                        int day=c.get(Calendar.DAY_OF_MONTH);
                        String dd=("DATE:"+day+"/"+(month+1)+"/"+year);
                        int second=c.get(Calendar.SECOND);
                        int minute=c.get(Calendar.MINUTE);
                        int hour=c.get(Calendar.HOUR);
                        String tt=("TIME:"+hour+":"+minute+":"+second);
            String notiadd="On "+dd+" at "+tt+" Money request of amount "+a+" was send to ADMIN;";
            JOptionPane.showMessageDialog(null,"Request Send to the ADMIN for adding Money.");
            update_notifications(notiadd);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
        finally{
                try{
                    rs.close();
                    pst.close();
//                    JOptionPane.showMessageDialog(null,"Closed");
                }
                catch(SQLException | HeadlessException e){

                }
            }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void boxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_boxItemStateChanged
        // TODO add your handling code here:
        String value=box.getSelectedItem().toString();
        if(value.equals("Day1")){
            Update_table_avail(1);
        }
        else if(value.equals("Day2")){
            Update_table_avail(2);
        }
        else if(value.equals("Day3")){
            Update_table_avail(3);
        } else {
        }    
    }//GEN-LAST:event_boxItemStateChanged
   
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        giveamount();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void givemoneytoadmin(int wm) throws SQLException{
        rs.close();
        pst.close();
        String Username=null;
        String Password=null;
        String Name=null;
        String Address=null;
        String Email=null;
        String Phoneno=null;
        String Activestatus=null;
        String Designation=null;
        String Institute=null;
        String City=null;
        String State=null;
        String Hall=null;
        String RoomNo=null;
        String Currentmoney=null;
        String Notifications=null;
        try{
            String sql="select * from Logindata where Designation = ?";
            pst= conn.prepareStatement(sql);
            pst.setString(1,"1");
            rs=pst.executeQuery();
            if(rs.next()){
                Username=rs.getString("Username");
                Password=rs.getString("Password");
                Name=rs.getString("Name");
                Address=rs.getString("Address");
                Email=rs.getString("Email");
                Phoneno=rs.getString("Phoneno");
                Activestatus=rs.getString("Activestatus");
                Currentmoney=rs.getString("Waletmoney");
                Designation=rs.getString("Designation");
                Institute=rs.getString("InstituteName");
                City=rs.getString("City");
                State=rs.getString("State");
                Hall=rs.getString("HallName");
                RoomNo=rs.getString("RoomNo");
                Notifications=rs.getString("Notifications");
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                JOptionPane.showMessageDialog(null,e);
            }
        }
        try{
                String sql="delete from Logindata where Username=?";
                pst= conn.prepareStatement(sql);
                pst.setString(1, Username);
                pst.execute();
                pst.close();
            }catch(Exception e){
                JOptionPane.showMessageDialog(null,e);
            }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                JOptionPane.showMessageDialog(null,e);            
            }
        }
//to change        
        try{
            String sql="Insert into Logindata (Username,Password,Name,Address,Email,Phoneno,Waletmoney,Activestatus,Designation,InstituteName,City,State,Moneydemand,HallName,RoomNo,Notifications) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            pst= conn.prepareStatement(sql);
            pst.setString(1, Username);
            pst.setString(2, Password);
            pst.setString(3, Name);
            pst.setString(4, Address);
            pst.setString(5, Email);
            pst.setString(6, Phoneno);
            pst.setString(7, Integer.toString(wm+(Integer.parseInt(Currentmoney))));
            pst.setString(8, Activestatus);
            pst.setString(9, Designation);
            pst.setString(10, Institute);
            pst.setString(11, City);
            pst.setString(12, State);
            pst.setString(13, "0");
            pst.setString(14, Hall);
            pst.setString(15, RoomNo);
            pst.setString(16, Notifications);
            pst.execute();
            pst.close();
//            JOptionPane.showMessageDialog(null,"Added HuRRay!!");
        }catch(SQLException | NumberFormatException | HeadlessException e){
            JOptionPane.showMessageDialog(null,e);
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                JOptionPane.showMessageDialog(null,e);            
            }
        }
}        
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
    try{
        rs.close();
        pst.close();
        int mango=giveamount();//amt to be deducted
        rs.close();
        pst.close();
        String s= room_1.getText(); //s=hallID
        String hn=get_hall_name(s); //hn=hallName
        int wm=0;
        int b= Integer.parseInt(stay_room.getSelectedItem().toString());
        String sql="select Waletmoney from Logindata where Username=?";
        pst= conn.prepareStatement(sql);
        pst.setString(1,user);
        rs=pst.executeQuery();
        if(rs.next()){            
            wm=Integer.parseInt(rs.getString("Waletmoney"));//wm=current wallet money
            rs.close();
            pst.close();
            if(wm>=mango){
                String hallNAME=hn;
                String room_no=update_rooms(s,b);
                rs.close();
                pst.close();
                        Calendar c=new GregorianCalendar();
                        int month=c.get(Calendar.MONTH);
                        int year=c.get(Calendar.YEAR);
                        int day=c.get(Calendar.DAY_OF_MONTH);
                        String dd=("DATE:"+day+"/"+(month+1)+"/"+year);
                        int second=c.get(Calendar.SECOND);
                        int minute=c.get(Calendar.MINUTE);
                        int hour=c.get(Calendar.HOUR);
                        String tt=("TIME:"+hour+":"+minute+":"+second);
                        String notiadd=("On "+dd+" at "+tt+" "+hallNAME+" is alloted with room: "+room_no+";");
                update(wm-mango,user,hallNAME,room_no);      //THREAD
                rs.close();
                pst.close();
                update_notifications(notiadd);
                rs.close();
                pst.close();
                JOptionPane.showMessageDialog(null,"Successful Booking!.");
                JOptionPane.showMessageDialog(null,"You have been alloted "+hallNAME+" hall of Residence");
                givemoneytoadmin(mango);
            }
            else{
                JOptionPane.showMessageDialog(null,"Oops!! You do not have enough Money. Please add money to your Wallet.");
            }
        }
        else
            {
                JOptionPane.showMessageDialog(null,"NOT BOOKED");
            }
    }
    catch(NumberFormatException | SQLException | HeadlessException e){
        JOptionPane.showMessageDialog(null,e);
    }finally{
        try{
            rs.close();
            pst.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
    }
    room_table();
        try{
            rs.close();
            pst.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void fareActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fareActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fareActionPerformed

    private void f_seatsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_f_seatsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_f_seatsActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        String s=f_id.getText();//  s=event id
        int count =Integer.parseInt(f_seats.getText());//count = number of seats
        int a= update_event(s,1,count);
                        Calendar c=new GregorianCalendar();
                        int month=c.get(Calendar.MONTH);
                        int year=c.get(Calendar.YEAR);
                        int day=c.get(Calendar.DAY_OF_MONTH);
                        String dd=("DATE:"+day+"/"+(month+1)+"/"+year);
                        int second=c.get(Calendar.SECOND);
                        int minute=c.get(Calendar.MINUTE);
                        int hour=c.get(Calendar.HOUR);
                        String tt=("TIME:"+hour+":"+minute+":"+second);
            String plus="On "+dd+" at "+tt;
        switch (a) {
            case -1:
                JOptionPane.showMessageDialog(null,"Oops!! Required Number of Seats are not Available");
                return;
            case 2:
                String addnoti=Integer.toString(count);
                if(count==1){
                    addnoti+=" seat has been booked for you!;";
                    update_notifications(plus+" "+count+" seat has been booked for you!;");
                    JOptionPane.showMessageDialog(null,count+ "seat has been booked for you!");
                    return;
                }
                else{
                    addnoti+=" seats has been booked for you!;";
                    JOptionPane.showMessageDialog(null,count+ "seats has been booked for you!");
                    update_notifications(plus+" "+addnoti);
                    return;
                }
            default:
                break;
        }
        int b= update_event(s,2,count);
        switch (b) {
            case -1:
                JOptionPane.showMessageDialog(null,"Oops!! Required Number of Seats are not Available");
                return;
            case 2:
                String addnoti=Integer.toString(count);
                if(count==1){
                    addnoti+=" seat has been booked for you!;";
                    update_notifications(plus+" "+count+" seat has been booked for you!;");
                    JOptionPane.showMessageDialog(null,count+ "seat has been booked for you!");
                    return;
                }
                else{
                    addnoti+=" seats has been booked for you!;";
                    JOptionPane.showMessageDialog(null,count+ "seats has been booked for you!");
                    update_notifications(plus+" "+addnoti);
                    return;
                }
            default:
                break;
        }
        int co= update_event(s,3,count);
        switch (co) {
            case -1:
                JOptionPane.showMessageDialog(null,"Oops!! Required Number of Seats are not Available");
                return;
            case 2:
                String addnoti=Integer.toString(count);
                if(count==1){
                    addnoti+=" seat has been booked for you!;";
                    update_notifications(plus+" "+count+" seat has been booked for you!;");
                    JOptionPane.showMessageDialog(null,count+ "seat has been booked for you!");
                    return;
                }
                else{
                    addnoti+=" seats has been booked for you!;";
                    JOptionPane.showMessageDialog(null,count+ "seats has been booked for you!");
                    update_notifications(plus+" "+addnoti);
                    return;
                }
            default:
                break;
        }
        JOptionPane.showMessageDialog(null,"PLEASE Enter a valid EventID.");
    }//GEN-LAST:event_jButton5ActionPerformed

    private void NotiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NotiActionPerformed

    }//GEN-LAST:event_NotiActionPerformed

    private void NotiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_NotiMouseClicked

    }//GEN-LAST:event_NotiMouseClicked

    private void pathActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pathActionPerformed
        // TODO add your handling code here:
        String dest=destination.getSelectedItem().toString();
        String sour=source.getSelectedItem().toString();
        boxbox.setText(doit(sour,dest));
        try {
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(Userhomepage.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            pst.close();
        } catch (SQLException ex) {
            Logger.getLogger(Userhomepage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_pathActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        Mapiit mm= new Mapiit();
        mm.setVisible(true);
    }//GEN-LAST:event_jButton4ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Userhomepage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Userhomepage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Userhomepage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Userhomepage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Userhomepage().setVisible(true);
            }
        });
    }
public static void computePaths(Vertex source)
    {
        source.minDistance = 0.;
        PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();
    vertexQueue.add(source);

    while (!vertexQueue.isEmpty()) {
        Vertex u = vertexQueue.poll();

            // Visit each edge exiting u
            for (Edge e : u.adjacencies)
            {
                Vertex v = e.target;
                double weight = e.weight;
                double distanceThroughU = u.minDistance + weight;
        if (distanceThroughU < v.minDistance) {
            vertexQueue.remove(v);

            v.minDistance = distanceThroughU ;
            v.previous = u;
            vertexQueue.add(v);
        }
            }
        }
    }

    public static List<Vertex> getShortestPathTo(Vertex target)
    {
        List<Vertex> path = new ArrayList<Vertex>();
        for (Vertex vertex = target; vertex != null; vertex = vertex.previous)
            path.add(vertex);

        Collections.reverse(path);
        return path;
    }

    public static String doit(String start,String end)
    {
        // mark all the vertices 
 Vertex A = new Vertex("LBS");
        Vertex B = new Vertex("MMM");
        Vertex C = new Vertex("LLR");
        Vertex D = new Vertex("AZAD");
        Vertex E = new Vertex("NEHRU");
        Vertex F = new Vertex("PATEL");
        Vertex G = new Vertex("BC ROY HOSPITAL");
        Vertex H = new Vertex("BRH");
        Vertex I = new Vertex("PAN LOOP");
        Vertex J = new Vertex("DIRECTOR's HOUSE");
        Vertex K = new Vertex("NALANDA COMPLEX");
        Vertex L = new Vertex("VIKRAMSHILA");
        Vertex M = new Vertex("MUSEUM");
        Vertex N = new Vertex("VGSOM");
        Vertex O = new Vertex("ARENA");
        Vertex P = new Vertex("SBI BANK");
        Vertex Q = new Vertex("TIKKA");
        Vertex R = new Vertex("SN");
        Vertex S = new Vertex("MT");
        Vertex T = new Vertex("GYMKHANA");
        Vertex U = new Vertex("BASKETBALL COURT");
        Vertex V = new Vertex("TENIS COURT");
        Vertex W = new Vertex("GYNAN GHOSH STADIUM");
        Vertex X = new Vertex("MAHATAMA GHANDI STADIUM");
        Vertex Y = new Vertex("TATA STEEL COMPLEX");
        Vertex Z = new Vertex("RP");
        Vertex Aa = new Vertex("RK");
        Vertex Ab = new Vertex("MS");
        Vertex Ac = new Vertex("TECH MARKET");
        Vertex Ad = new Vertex("MAIN BUILDING");
        Vertex Ae = new Vertex("CENTRAL 4 WAY");
        // Vertex Ac = new Vertex("PATEL");
A.adjacencies = new Edge[]{ new Edge(B, 1) , new Edge(I,3) };
        B.adjacencies = new Edge[]{ new Edge(A, 1), new Edge(C,1)};
        C.adjacencies = new Edge[]{ new Edge(B, 1),new Edge(U,1) };
        D.adjacencies = new Edge[]{ new Edge(I, 3),new Edge(G,2) };
        E.adjacencies = new Edge[]{ new Edge(F, 1) };
        F.adjacencies = new Edge[]{ new Edge(I, 1),new Edge(E, 1) };
        G.adjacencies = new Edge[]{ new Edge(D, 2), new Edge(Ac, 8),new Edge(T, 6),new Edge(G, 10) };
        H.adjacencies = new Edge[]{ new Edge(Ac, 2) };
        I.adjacencies = new Edge[]{ new Edge(A, 3),new Edge(F, 1),new Edge(V, 2),new Edge(D, 3),new Edge(T, 4) };
        J.adjacencies = new Edge[]{ new Edge(Ac, 8),new Edge(G, 10),new Edge(R, 4),new Edge(S, 4),new Edge(T, 5) };
        K.adjacencies = new Edge[]{ new Edge(X, 1),new Edge(M, 8),new Edge(L, 11) };
        L.adjacencies = new Edge[]{ new Edge(M, 7),new Edge(K, 11),new Edge(N, 6),new Edge(Y, 6),new Edge(O, 5)};
        M.adjacencies = new Edge[]{ new Edge(N, 2),new Edge(K, 8) ,new Edge(L, 7)};
        N.adjacencies = new Edge[]{ new Edge(L, 6),new Edge(M, 2),new Edge(O, 5) };
        O.adjacencies = new Edge[]{ new Edge(Y, 3),new Edge(N, 5),new Edge(L, 5) ,new Edge(Ad, 1)};
        P.adjacencies = new Edge[]{ new Edge(Z, 3),new Edge(Ae, 5) };
        Q.adjacencies = new Edge[]{ new Edge(Y, 0.5),new Edge(R, 1),new Edge(S, 1),new Edge(Ae, 3.2) };
        R.adjacencies = new Edge[]{ new Edge(J, 4),new Edge(S, 0.3),new Edge(Q, 1),new Edge(Ae, 3) };
        S.adjacencies = new Edge[]{ new Edge(R, 0.3),new Edge(J, 4),new Edge(Q, 1) ,new Edge(Ae, 3)};
        T.adjacencies = new Edge[]{ new Edge(J, 5),new Edge(U, 2),new Edge(V, 2),new Edge(I, 4),new Edge(G, 6),new Edge(Ac, 8),new Edge(W, 1) };
        U.adjacencies = new Edge[]{ new Edge(T, 2),new Edge(Ab, 1),new Edge(C, 1) };
        V.adjacencies = new Edge[]{ new Edge(T, 2),new Edge(I, 2) };
        W.adjacencies = new Edge[]{ new Edge(T, 1),new Edge(Ae, 3.9) };
        X.adjacencies = new Edge[]{ new Edge(K, 1) };
        Y.adjacencies = new Edge[]{ new Edge(Q, 0.5),new Edge(O, 3),new Edge(L, 6) };
        Z.adjacencies = new Edge[]{ new Edge(Aa, 0.5),new Edge(Ae, 2.9),new Edge(P, 3) };
        Aa.adjacencies = new Edge[]{ new Edge(Z, 0.5),new Edge(Ab, 3) };
        Ab.adjacencies = new Edge[]{ new Edge(U, 1),new Edge(Aa, 3) };
        Ac.adjacencies = new Edge[]{ new Edge(G, 8),new Edge(H, 2),new Edge(J, 8),new Edge(T, 8) };
        Ad.adjacencies = new Edge[]{ new Edge(O, 1),new Edge(Ae, 0.1) };
        Ae.adjacencies = new Edge[]{ new Edge(P, 5),new Edge(Z, 2.9),new Edge(Q, 3.2),new Edge(R, 3),new Edge(S, 3),new Edge(W, 3.9),new Edge(Ad, 0.1) };
        
        // set the edges and weight
        
        if(start.equals("LBS"))
            computePaths(A);
        else if(start.equals("MMM"))
            computePaths(B);
        else if(start.equals("LLR"))
            computePaths(C);
        else if(start.equals("CENTRAL 4 WAY"))
            computePaths(Ae);
        else if(start.equals("AZAD"))
            computePaths(D);
        else if(start.equals("NEHRU"))
            computePaths(E);
        else if(start.equals("PATEL"))
            computePaths(F);
        else if(start.equals("BC ROY HOSPITAL"))
            computePaths(G);
        else if(start.equals("BRH"))
            computePaths(H);
        else if(start.equals("PAN LOOP"))
            computePaths(I);
        else if(start.equals("DIRECTOR's HOUSE"))
            computePaths(J);
        else if(start.equals("NALANDA COMPLEX"))
            computePaths(K);
        else if(start.equals("VIKRAMSHILA"))
            computePaths(L);
        else if(start.equals("MUSEUM"))
            computePaths(M);
        else if(start.equals("VGSOM"))
            computePaths(N);
        else if(start.equals("ARENA"))
            computePaths(O);
        else if(start.equals("SBI BANK"))
            computePaths(P);
        else if(start.equals("TIKKA"))
            computePaths(Q);
        else if(start.equals("SN"))
            computePaths(R);
        else if(start.equals("MT"))
            computePaths(S);
        else if(start.equals("GYMKHANA"))
            computePaths(T);
        else if(start.equals("BASKETBALL COURT"))
            computePaths(U);
        else if(start.equals("TENIS COURT"))
            computePaths(V);
        else if(start.equals("GYNAN GHOSH STADIUM"))
            computePaths(W);
        else if(start.equals("MAHATAMA GHANDI STADIUM"))
            computePaths(X);
        else if(start.equals("TATA STEEL COMPLEX"))
            computePaths(Y);
        else if(start.equals("RP"))
            computePaths(Z);
        else if(start.equals("RK"))
            computePaths(Aa);
        else if(start.equals("MS"))
            computePaths(Ab);
        else if(start.equals("TECH MARKET"))
            computePaths(Ac);
        else if(start.equals("MAIN BUILDING"))
            computePaths(Ad);
        else{}
        List<Vertex> path = null;
        if(end.equals("LBS"))
            path=getShortestPathTo(A);
        else if(end.equals("MMM"))
            path=getShortestPathTo(B);
        else if(end.equals("LLR"))
            path=getShortestPathTo(C);
        else if(end.equals("AZAD"))
            path=getShortestPathTo(D);
         else if(start.equals("CENTRAL 4 WAY"))
            path=getShortestPathTo(Ae);
        else if(end.equals("NEHRU"))
            path=getShortestPathTo(E);
        else if(end.equals("PATEL"))
            path=getShortestPathTo(F);
        else if(end.equals("BC ROY HOSPITAL"))
            path=getShortestPathTo(G);
        else if(end.equals("BRH"))
            path=getShortestPathTo(H);
        else if(end.equals("PAN LOOP"))
            path=getShortestPathTo(I);
        else if(end.equals("DIRECTOR's HOUSE"))
            path=getShortestPathTo(J);
        else if(end.equals("NALANDA COMPLEX"))
            path=getShortestPathTo(K);
        else if(end.equals("VIKRAMSHILA"))
            path=getShortestPathTo(L);
        else if(end.equals("MUSEUM"))
            path=getShortestPathTo(M);
        else if(end.equals("VGSOM"))
            path=getShortestPathTo(N);
        else if(end.equals("ARENA"))
            path=getShortestPathTo(O);
        else if(end.equals("SBI BANK"))
            path=getShortestPathTo(P);
        else if(end.equals("TIKKA"))
            path=getShortestPathTo(Q);
        else if(end.equals("SN"))
            path=getShortestPathTo(R);
        else if(end.equals("MT"))
            path=getShortestPathTo(S);
        else if(end.equals("GYMKHANA"))
            path=getShortestPathTo(T);
        else if(end.equals("BASKETBALL COURT"))
            path=getShortestPathTo(U);
        else if(end.equals("TENIS COURT"))
            path=getShortestPathTo(V);
        else if(end.equals("GYNAN GHOSH STADIUM"))
            path=getShortestPathTo(W);
        else if(end.equals("MAHATAMA GHANDI STADIUM"))
            path=getShortestPathTo(X);
        else if(end.equals("TATA STEEL COMPLEX"))
            path=getShortestPathTo(Y);
        else if(end.equals("RP"))
            path=getShortestPathTo(Z);
        else if(end.equals("RK"))
            path=getShortestPathTo(Aa);
        else if(end.equals("MS"))
            path=getShortestPathTo(Ab);
        else if(end.equals("TECH MARKET"))
            path=getShortestPathTo(Ac);
        else if(end.equals("MAIN BUILDING"))
            path=getShortestPathTo(Ad);
        else{} 
//computePaths();
//        System.out.println("Distance to " + Ad + ": " + Ad.minDistance);
//        List<Vertex> path = getShortestPathTo(L);//end
//        System.out.println("Path: " + path);
        return (""+path);
    }    
    //"apple".substring(3);
    //s.substring(0, Math.min(s.length(), n));
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> Noti;
    private javax.swing.JTextField add_money;
    private javax.swing.JTextField avai_seats;
    private javax.swing.JTextField avail_event_id;
    private javax.swing.JProgressBar avail_progress;
    private javax.swing.JTable avail_table;
    private javax.swing.JComboBox<String> box;
    private javax.swing.JTextField boxbox;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel dates;
    private javax.swing.JComboBox<String> destination;
    private javax.swing.JTextField f_id;
    private javax.swing.JTextField f_seats;
    private javax.swing.JTextField fare;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField p_address;
    private javax.swing.JTextField p_city;
    private javax.swing.JTextField p_email;
    private javax.swing.JTextField p_institute;
    private javax.swing.JTextField p_mobile;
    private javax.swing.JTextField p_name;
    private javax.swing.JTextField p_state;
    private javax.swing.JTextField p_wallet;
    private javax.swing.JButton path;
    private javax.swing.JTextField room_1;
    private javax.swing.JTable room_book;
    private javax.swing.JComboBox<String> source;
    private javax.swing.JComboBox<String> stay_day;
    private javax.swing.JComboBox<String> stay_room;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JLabel times;
    private javax.swing.JTextField total_seats;
    // End of variables declaration//GEN-END:variables
}



