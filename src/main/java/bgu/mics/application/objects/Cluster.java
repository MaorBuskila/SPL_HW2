package bgu.mics.application.objects;


import java.util.Queue;
import java.util.Vector;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Passive object representing the cluster.
 * <p>
 * This class must be implemented safely as a thread-safe singleton.
 * Add all the fields described in the assignment as private fields.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */
public class Cluster {
	private Vector<GPU> gpus;
	private Vector<CPU> cpus ;
	//private Queue<DataBatch> UnprocessedBatch;

	private ConcurrentHashMap<DataBatch,GPU> dataBatchToGpu;

	private BlockingQueue<DataBatch> unProcessedBatch;
	private BlockingQueue<DataBatch> processedBatch;

	private Vector<String> trainedModels;
	private int totalDataBatchProcessedCpu; // TODO: CHECK
	private ConcurrentHashMap<CPU,Integer> cpuTimeUnitUsed;
	private ConcurrentHashMap<GPU,Integer> gpuTimeUnitUsed ;
	private static Cluster cluster = null;


	public Cluster()
	{
		gpus = new Vector<>();
		cpus = new Vector<>();
		dataBatchToGpu=new ConcurrentHashMap<>();
		unProcessedBatch=new LinkedBlockingDeque<>();
		processedBatch=new LinkedBlockingDeque<>();
		trainedModels=new Vector<>();
		cpuTimeUnitUsed = new ConcurrentHashMap<>();
		gpuTimeUnitUsed = new ConcurrentHashMap<>();
		totalDataBatchProcessedCpu =0;

	}
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
		this.totalDataBatchProcessedCpu +=1;
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

	public void addGpuTimeUnitUsed(GPU gpu1) {

		Integer x=gpuTimeUnitUsed.get(gpu1);
		x++;
		gpuTimeUnitUsed.replace(gpu1,x); // TODO Check how to simplify this

	}

	public Queue<DataBatch> sendProcessedToGPU()
	{
		while(!processedBatch.isEmpty()) // DataBatchim - data - name - GPU
		{
			DataBatch tmpDB = processedBatch.poll();
			dataBatchToGpu.get(tmpDB).reciveProcessedDataBatch(tmpDB); //send to desired GPU the db
			//(GPU)(processedBatch.poll().getKey()).;


		}
		return null; //TODO: delete
	}

	public Queue<DataBatch> getUnProcessedDataBatch()
	{
		return this.unProcessedBatch;
	}

	public void addToUnprocessedBatch(DataBatch dataBatch, GPU gpu) {
		unProcessedBatch.add(dataBatch);
		dataBatchToGpu.put(dataBatch,gpu);
//		if(dataBatchToGpu.containsKey(gpu)) {
//			dataBatchToGpu.get(gpu).addElement
//		}
// 		GPUtoModels.put(gpu, )
	}
	public void addToProcessed(DataBatch dataBatch) {
		processedBatch.add(dataBatch);
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
