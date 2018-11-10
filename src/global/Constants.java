package global;

public final class Constants {
	private Constants(){} // Prevent instantiation

	public final static class CellGridOptions {
		private CellGridOptions(){} // Prevent instantiation
		public static int rows = 10;
		public static int columns = 10;
	}

	public static class FrameOptions {
		private FrameOptions(){} // Prevent instantiation
		public static int borderWidth = 1;
		public static int scalingFactor = 35;
		/* These are used to add some whitespace surrounding the grid */
		public static int xWhiteSpaceOffset = 25;
		public static int yWhiteSpaceOffset = 50;
	}

	public static class PlayerOptions {
		private PlayerOptions(){}
		public static int numLastMoves = 5;
	}

	public static class RunningOptions {
		private RunningOptions(){} // Prevent instantiation
		public static int delay = 0;
		public static boolean printMoves = false;
		public static int cycles = 1000000;
		public static boolean testing = true;
	}

	public static class TrainingOptions {
		private TrainingOptions(){} // Prevent instantiation
	}

	public static class UnitOptions {
		private UnitOptions(){} // Prevent instantiation
		public static int numberOfPlayers = 5;
		public static int numberOfBlocks = 4;
		public static int minBlockLength = 1;
		public static int maxBlockLength = 2;
	}
}
