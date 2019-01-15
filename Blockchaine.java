
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Blockchaine {

	private int difficulty;
	List<Block> blocks;


	List<Block> getBlocks() {
		return blocks;
	}

	void setBlocks(List<Block> blocks) {
		this.blocks = blocks;
	}

	public Blockchaine(int difficulty) {
		this.difficulty = difficulty;
		blocks = new ArrayList<>();
		// creation du premier block
		Block b = new Block(1, 
				System.currentTimeMillis(),
				null, 
				"First Block"
				);
		
		

		b.mineBlock(difficulty);
		blocks.add(b);
		
	}


	public Blockchaine(List<Block> clone) {///// CST CLONAGE
		this.difficulty = 4;
		blocks = new ArrayList<>();
		
		for(Block aa : clone) {
			aa.mineBlock(4);
			this.blocks.add(new Block(aa));
		}

	}

	public int getDifficulty() {
		return difficulty;
	}

	public Block latestBlock() {
		return blocks.get(blocks.size() - 1);
	}

	public Block newBlock(String data) { 
		return new Block(latestBlock().getIndex() + 1,
			System.currentTimeMillis(),
			latestBlock().getHash(),
			data);
	}

	public void addBlock(Block b) {
		if (b != null) {
			b.mineBlock(difficulty);
			blocks.add(b);
		}
	}




	public boolean isFirstBlockValid() {

		if  (blocks.get(0).getIndex() != 0 ||
				blocks.get(0).getPreviousHash() != null ||
				(blocks.get(0).getHash() == null || !Block.calculateHash(blocks.get(0)).equals(blocks.get(0).getHash())) ){
			return false;
		}

		return true;
	}



	public boolean isValidNewBlock(Block newBlock, Block previousBlock) {
		if (newBlock != null  &&  previousBlock != null) {

			if ( (previousBlock.getIndex() + 1 != newBlock.getIndex()) 
					|| newBlock.getPreviousHash() == null  ||  !newBlock.getPreviousHash().equals(previousBlock.getHash()  ) 
					|| (newBlock.getHash() == null  ||   !Block.calculateHash(newBlock).equals(newBlock.getHash() )  )      ) {
				return false;
			}
			return true;
		}

		return false;
	}

	public boolean isBlockChainValid() {
		if (!isFirstBlockValid()) {
			return false;
		}

		for (int i = 1; i < blocks.size(); i++) {
			Block currentBlock = blocks.get(i);
			Block previousBlock = blocks.get(i - 1);
			if (!isValidNewBlock(currentBlock, previousBlock))
				return false;
		}

		return true;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();

		for (Block block : blocks) {
			builder.append(block).append("\n");
		}

		return builder.toString();
	}

	private String getData(Block a) {
		return a.getData();
	}

	public int tailleblocks() {
		return blocks.size();
	}

}