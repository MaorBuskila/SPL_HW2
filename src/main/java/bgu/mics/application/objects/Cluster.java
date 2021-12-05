package bgu.mics.application.objects;


import java.util.Queue;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Passive object representing the cluster.
 * <p>
 * This class must be implemented safely as a thread-safe singleton.
 * Add all the fields described in the assignment as private fields.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */
public class Cluster {
	private Vector<GPU> gpus;
	private Vector<CPU> cpus;
	private Queue<DataBatch> UnprocessedBatch;
	private Queue<DataBatch> processedBatch;


	/**
     * Retrieves the single instance of this class.
     */
	public static Cluster getInstance() {
		//TODO: Implement this
		return null;
	}

	public Queue<DataBatch> getprocessedBatch()
	{
		return this.processedBatch;
	}
	public void addToProcessed(DataBatch dataBatch) {
		processedBatch.add(dataBatch);
	}
	public void addToUnprocessedBatch(DataBatch dataBatch) {
		UnprocessedBatch.add(dataBatch);
	}
}
