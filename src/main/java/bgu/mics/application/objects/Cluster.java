package bgu.mics.application.objects;


import java.util.Iterator;
import java.util.Queue;
import java.util.Vector;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

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
	private ConcurrentHashMap<CPU,BlockingQueue<DataBatch> > unProcessedQueues;
//	private BlockingQueue<DataBatch> processedBatch;

	//**Statitics***************************
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
		unProcessedQueues=new ConcurrentHashMap<>();
	//	processedBatch=new LinkedBlockingQueue<>();
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



	public Queue<DataBatch> getUnProcessedQueue()
	{
		return this.unProcessedBatch;
	}

	public void addToUnprocessedMap(DataBatch dataBatch, GPU gpu) {
		dataBatchToGpu.put(dataBatch,gpu);
		unProcessedQueues.get(minFutureTime()).add(dataBatch);

//		if(dataBatchToGpu.containsKey(gpu)) {
//			dataBatchToGpu.get(gpu).addElement
//		}
// 		GPUtoModels.put(gpu, )
	}
	public void sendToGPU(DataBatch dataBatch) {
		synchronized (this.dataBatchToGpu) {

			dataBatchToGpu.get(dataBatch).reciveProcessedDataBatch(dataBatch);
			dataBatchToGpu.remove(dataBatch);
		}
		//send back to GPU ? ?because  we must to send to the fit GPU
	}

	public CPU minFutureTime()
	{
		int sum;
		int min=Integer.MAX_VALUE;
		CPU minTimeWork=null;
		synchronized (unProcessedQueues) {
			for (CPU key : unProcessedQueues.keySet()) {
				Queue q = unProcessedQueues.get(key);
				int numberOfCores = key.getNumberOfCores();
				Iterator<DataBatch> itr=q.iterator();
				sum=0;
				while(itr.hasNext())
				{
					DataBatch db=itr.next();
					if(db.getType()=="Images")
						sum+=(32/numberOfCores)*4;
					if(db.getType()=="Text")
						sum+=(32/numberOfCores)*2;
					if(db.getType()=="Tabular")
						sum+=(32/numberOfCores);
				}
				if(sum<min) {
					min = sum;
					minTimeWork=key;
				}

			}

		}
		return minTimeWork;

	}


	public void addToCPUS(CPU cpu) {
		cpus.addElement(cpu);
		cpuTimeUnitUsed.put(cpu,0);
		BlockingQueue<DataBatch> q=new LinkedBlockingQueue<>();
		unProcessedQueues.put(cpu,q);
	}
	public void addToGPUS(GPU gpu)
	{
		gpus.addElement(gpu);
		gpuTimeUnitUsed.put(gpu,0);
	}
}
