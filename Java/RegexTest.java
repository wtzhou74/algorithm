
public class RegexTest {

	public static void main(String[] args) {
		String[] quotes = new String[] {
		"Elmo is 9 the hot_test of the season! Elmo will be on every kid's wishlist!"
//		"The new Elmo dolls are super high quality",
//		"Expect the Elsa dolls to be very popular this year, Elsa!",
//		"Elsa and Elmo are the toys I'll be buying for my kids, Elsa is good",
//		"For parents of older kids, look into buying them a drone",
//		"Warcraft is slowly rising in popularity ahead of the holiday season"
		};
		for (String quote : quotes) {
			String[] words = quote.split("\\W+");
			for (String word : words) {
				System.out.println(word);
			}
			
		}
		
	}
}
