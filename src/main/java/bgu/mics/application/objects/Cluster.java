package bgu.mics.application.objects;


import bgu.mics.MessageBusImpl;

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
	private Vector<String> trainedModels;
	private int totalDataBatchProccessedbyCpu;
	private ConcurrentHashMap<CPU,Integer> cpuTimeUnitUsed;
	private ConcurrentHashMap<GPU,Integer> gpuTimeUnitUsed;
	private static Cluster cluster = null;



	/**
	 *
     * Retrieves the single instance of this class.
     */
	public static Cluster getInstance() {
			if (cluster == null) {
				cluster =new Cluster();
			}

			return cluster;
		}


	public void updateTotalDataBatchProccessedbyCpu()
	{
		this.totalDataBatchProccessedbyCpu+=1;
	}
	public void addToTrainedModels(String name)
	{
		trainedModels.addElement(name);
	}
	public void addCpuTimeUnitUsed(CPU cpu1)
	{

			Integer x=cpuTimeUnitUsed.get(cpu1);
			x++;
			cpuTimeUnitUsed.replace(cpu1,x); // TODO Check how to simplify this


	}
	public void addGpuTimeUnitUsed(GPU gpu1)
	{

		Integer x=gpuTimeUnitUsed.get(gpu1);
		x++;
		gpuTimeUnitUsed.replace(gpu1,x); // TODO Check how to simplify this


	}

	public Queue<DataBatch> getProcessedDataBatch()
	{
		return this.processedBatch;
	}
	public Queue<DataBatch> getUnProcessedDataBatch()
	{
		return this.UnprocessedBatch;
	}

	public void addToProcessed(DataBatch dataBatch) {
		processedBatch.add(dataBatch);
	}
	public void addToUnprocessedBatch(DataBatch dataBatch) {
		UnprocessedBatch.add(dataBatch);
	}

	public void addToCPUS(CPU cpu) {
		cpus.addElement(cpu);
		cpuTimeUnitUsed.put(cpu,0);
	}
	public void addToGPUS(GPU gpu)
	{
		gpus.addElement(gpu);
		gpuTimeUnitUsed.put(gpu,0);
	}
}
