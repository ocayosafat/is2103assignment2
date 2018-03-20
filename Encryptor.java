import java.util.*;

class Encryptor {

	private String alphabet = "abcdefghijklmnopqrstuvwxyz";
	private String jumbled = "dqwtmxjhusarvpinfogcyzkble";
	private String number = "1234567890";
	private String numberCover = "4706839251";
	private StringBuilder sb = new StringBuilder();
	private String rawText;

	public static void main(String[] args) {

		Encryptor prog = new Encryptor();
		prog.run();
	}

	public void run() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Input Message to be Encrypted: ");

		while (sc.hasNextLine()) {
			sb.append(sc.nextLine());
		}

		rawText = sb.toString();      

		for (int i = 0; i < 26; i++) {
			rawText = rawText.replaceAll(String.valueOf(alphabet.charAt(i)), String.valueOf(jumbled.charAt(i)));
		}

		for (int j = 0; j < 10; j++) {
			rawText = rawText.replaceAll(String.valueOf(number.charAt(j)), String.valueOf(numberCover.charAt(j)));
		}

		System.out.println(rawText);
	}
}