package net.sourceforge.subsonic.util;

public class RatingUtil {

	public static int windowsToSubsonic(String rating) {
		int rawRating = Integer.parseInt(rating);
		switch (rawRating) {
		case 255:
			return 5;
		case 196:
			return 4;
		case 128:
			return 3;
		case 64:
			return 2;
		case 1:
			return 1;

		default:
			return 0;
		}
	}

	public static long subsonicToWindows(int rating) {
		switch (rating) {
		case 0:
			return 0;
		case 1:
			return 1;
		case 2:
			return 64;
		case 3:
			return 128;
		case 4:
			return 196;
		case 5:
			return 255;

		default:
			return 0;
		}
	}
}
