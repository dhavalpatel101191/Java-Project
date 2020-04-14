

public class Detail {

	private String Vanue;
	private String Concert;
	private int Price;
	private String Date;
	private int Balance;
	private String Type;
	private int CBalance;
	private int CAmount;
	
	private static int Num = 0;
	public Detail() {}
	
	public void setVanue(String Vanue) {this.Vanue=Vanue;}
	public String getVanue() {return Vanue;}
	public void setConcert(String Concert) {this.Concert=Concert;}
	public String getConcert() {return Concert;}
	public void setType(String Type) {this.Type=Type;}
	public String getType() {return Type;}
	public void setPrice(int Price) {this.Price=Price;}
	public int getPrice() {return Price;}
	public void setDate(String Date) {this.Date=Date;}
	public String getDate() {return Date;}
	public void setBalance(int Balance) {this.Balance=Balance;}
	public int getBalance() {return Balance;}
	public void setCBalance(int Cbalance) {this.CBalance=Cbalance;}
	public int getCBalance() {return CBalance;}
	public void setCAmount(int CAmount) {this.CAmount = CAmount;}
	public int getCAmount() {return CAmount;}
	
	public static void setNum(int a) {Num+=a;}
	public static int getNum() {return Num;}
}
