public void update(){
        String a=add_money.getText();
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
            JOptionPane.showMessageDialog(null,user);
            String sql="select * from Logindata where Username = ?";
            JOptionPane.showMessageDialog(null,"boy");
            pst= conn.prepareStatement(sql);
            JOptionPane.showMessageDialog(null,"now");
            pst.setString(1,user);
            JOptionPane.showMessageDialog(null,"b");
            rs=pst.executeQuery();
            JOptionPane.showMessageDialog(null,"c");
            if(rs.next()){
//                Username=rs.getString("Username");
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
                System.out.println("money:");
                System.out.println(Moneyd);
                System.out.println("name "+Password);
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
                pst.setString(1, user);
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
        try{
            String sql="Insert into Logindata (Username,Password,Name,Address,Email,Phoneno,Waletmoney,Activestatus,Designation,InstituteName,City,State,Moneydemand) values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
            pst= conn.prepareStatement(sql);
            pst.setString(1, user);
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
            int j=Integer.parseInt(a);
            int i=Integer.parseInt(Moneyd);            
            JOptionPane.showMessageDialog(null,"done");
            i=i+j;
            String temp=""+i;
            JOptionPane.showMessageDialog(null,"done!!");
            pst.setString(13, temp);
            pst.execute();
            pst.close();
            JOptionPane.showMessageDialog(null,"Added HuRRay!!");
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
}       