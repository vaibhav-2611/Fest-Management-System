private void update(int wm){
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
            }
        }
            catch(Exception e){
                    JOptionPane.showMessageDialog(null,"ErrOR!!!!!!!");
            }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
            }
        try{
                String sql="delete from Logindata where Username=?";
                pst= conn.prepareStatement(sql);
                pst.setString(1, Username);
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
            String sql="Insert into Logindata (Username,Password,Name,Address,Email,Phoneno,Waletmoney,Activestatus,Designation,InstituteName,City,State,Moneydemand) values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
            pst= conn.prepareStatement(sql);
            pst.setString(1, Username);
            pst.setString(2, Password);
            pst.setString(3, Name);
            pst.setString(4, Address);
            pst.setString(5, Email);
            pst.setString(6, Phoneno);
            pst.setString(7, wm);
            pst.setString(8, Activestatus);
            pst.setString(9, Designation);
            pst.setString(10, Institute);
            pst.setString(11, City);
            pst.setString(12, State);
            pst.setString(13, "0");
            pst.execute();
            pst.close();
            JOptionPane.showMessageDialog(null,"Added HuRRay!!");
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
}        