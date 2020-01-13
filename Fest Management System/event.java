private int update_event(String s,int day,int count){
        String Eventid=s;
        String Starttime=null;
        String Endtime=null;
        String Venue=null;
        String Description=null;
        String Seats=null;
        String Eventname=null;
        String Availableseats=null;
        try{
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
                Availableseats=rs.getString("Activestatus");
            }
        }
            catch(Exception e){
                    JOptionPane.showMessageDialog(null,"ErrOR!!!!!!!");
            }finally{
            try{
                rs.close();
                    pst.close();
                if(Integer.parseInt(Availableseats)>=count)
                    { }
                    else
                       {//  JOptionPane.showMessageDialog(null,"Oops!! "+count +" Seat not Available");  
                        return 0 ;}
            }catch(Exception e){
            }
            }
        try{
                String sql="delete from Day"+day+"events where Eventid=?";
                pst= conn.prepareStatement(sql);
                pst.setString(1, Eventid);
                pst.execute();
                pst.close();
                JOptionPane.showMessageDialog(null,"DeLeTeD HuRRay!!");

            }catch(Exception e){
                JOptionPane.showMessageDialog(null,e);
            }finally{
            try{
                pst.close();
            }catch(Exception e){    
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
            pst.close();
            JOptionPane.showMessageDialog(null,"Added HuRRay!!");
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
        return 1;
}