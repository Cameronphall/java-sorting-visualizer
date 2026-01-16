package com.javavisualizer;
import java.awt.Color;
import java.awt.FlowLayout;
import java.util.Random;
import java.util.ArrayList;
import javax.swing.JPanel;
import java.awt.Graphics;


public class SortingPanel extends JPanel{
    private int currentCompareIndex = -1;
    private ArrayList<Integer> nums;
    private boolean isSorting = false;
    private int swapCount = 0;
    private long startTime = 0;
    private long endTime = 0;
    private int movingIndex = -1;
    private int eliminations = 0;
    private String currentAlgorithm = "";

    public SortingPanel(){
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
        this.setBackground(Color.CYAN);
        nums = new ArrayList<>(50);
        Random rand = new Random();
        for(int i = 0; i < 50; i++){
            nums.add(rand.nextInt(100) + 1);
        }
    }
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        int width = getWidth();
        int height = getHeight();

        int barCount = nums.size();

        int barWidth = width / barCount;

        for(int i = 0; i < barCount; i++){
            int value = nums.get(i);
            int barHeight = (int) ((value / 100.0) * height);
            int x = i * barWidth;
            int y = height - barHeight;

            if (i == movingIndex) {
                g.setColor(Color.LIGHT_GRAY);  // Moving bar in blue
            }else if(currentCompareIndex >= 0 && (i == currentCompareIndex || i == currentCompareIndex + 1)){
                g.setColor(Color.LIGHT_GRAY);
            } else{
                g.setColor(Color.GRAY);
            }
            g.fillRect(x, y, barWidth, barHeight);
            g.setColor(Color.BLACK);
            g.drawRect(x, y, barWidth, barHeight);
        }
        g.setColor(Color.BLACK);
        
        if(currentAlgorithm.equals("Stalin Sort")){
            g.drawString("Eliminations " + eliminations, 10, 20);
        }else{
            g.drawString("Swaps " + swapCount, 10, 20);
        }
        if(isSorting) {
            g.drawString("Sorting...", 10, 40);
        }else if(endTime > 0){
            long runtime = endTime - startTime;
            g.drawString("Time: " + runtime + " ms", 10, 40);
        }
    }

    public void generateNewBars(){
        if(isSorting)return;
        nums.clear();
        Random rand = new Random();
        for(int i = 0; i < 50; i++){
            nums.add(rand.nextInt(100) + 1);
        }
    }

    public void startBubbleSort() {
    currentAlgorithm = "Bubble Sort";
    swapCount = 0;
    startTime = System.currentTimeMillis();
    if(isSorting) return;
    isSorting = true;
    new Thread(() -> {
        int n = nums.size();
        try{
            for(int i = 0; i < n - 1; i++){
                for(int j = 0; j < n - 1; j++){
                    currentCompareIndex = j;
                    if(nums.get(j) > nums.get(j + 1)){
                        int temp = nums.get(j);
                        nums.set(j, nums.get(j + 1));
                        nums.set(j + 1, temp);
                        swapCount++;
                        repaint();
                        Thread.sleep(50);
                    }
                }
            }
            
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        currentCompareIndex = -1;
        endTime = System.currentTimeMillis();
        isSorting = false;
        repaint();

    }).start();
    }

    public void startInsertionSort() {
    currentAlgorithm = "Insertion Sort";
    swapCount = 0;
    startTime = System.currentTimeMillis();
    if(isSorting)return;
    isSorting = true;
    new Thread(() -> {
        int n = nums.size();
        try{
            for(int i = 1; i < n; i++){
                int j = i;
                currentCompareIndex = j;
                while(j > 0 && nums.get(j) < nums.get(j - 1)){
                    int temp = nums.get(j);
                    nums.set(j, nums.get(j - 1));
                    nums.set(j - 1, temp);
                    swapCount++;
                    movingIndex = j;
                    j--;
                    repaint();
                    Thread.sleep(50);
                }
            }
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        movingIndex = -1;
        currentCompareIndex = -1;
        endTime = System.currentTimeMillis();
        isSorting = false;
        repaint();
    }).start();
    }

    public void startSelectionSort() {
    currentAlgorithm = "Selection Sort";
    swapCount = 0;
    startTime = System.currentTimeMillis();
    if(isSorting) return;
    isSorting = true;
    new Thread(() -> {
        int n = nums.size();
        try{
            for(int i = 0; i < n - 1; i++){
                int smallIndex = i;
                for(int j = i + 1; j < n; j++){
                    currentCompareIndex = j;
                    repaint();
                    Thread.sleep(10);
                    if(nums.get(j) < nums.get(smallIndex)){
                        smallIndex = j;
                    }
                }
                currentCompareIndex = i;
                int temp = nums.get(i);
                nums.set(i, nums.get(smallIndex));
                nums.set(smallIndex, temp);
                swapCount++;
                repaint();
                Thread.sleep(50);
                currentCompareIndex = -1;
            }
            
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        currentCompareIndex = -1;
        endTime = System.currentTimeMillis();
        isSorting = false;
        repaint();

    }).start();
    }

    public int partition(int lowIndex, int highIndex) throws InterruptedException{
        
            int midPoint = lowIndex + (highIndex - lowIndex) / 2;
            int pivot = nums.get(midPoint);
            boolean done = false;
            while(!done){
                while(nums.get(lowIndex) < pivot){
                    currentCompareIndex = lowIndex;
                    repaint();
                    Thread.sleep(10);
                    lowIndex++;
                }
                while(nums.get(highIndex) > pivot){
                    currentCompareIndex = highIndex;
                    repaint();
                    Thread.sleep(10);
                    highIndex--;
                }

                if(lowIndex >= highIndex){
                    done = true;
                }else{
                    int temp = nums.get(lowIndex);
                    nums.set(lowIndex, nums.get(highIndex));
                    nums.set(highIndex, temp);
                    swapCount++;
                    lowIndex++;
                    highIndex--;
                    repaint();
                    Thread.sleep(50);
                }
            }
            return highIndex;
    }

    public void quickSort(int lowIndex, int highIndex) throws InterruptedException{
        if(lowIndex < highIndex){
            int pivot = partition(lowIndex, highIndex);
            quickSort(lowIndex, pivot);
            quickSort(pivot + 1, highIndex);
        }
    }

    public void startQuickSort() {
    currentAlgorithm = "Quick Sort";
    swapCount = 0;
    startTime = System.currentTimeMillis();
    if(isSorting) return;
    isSorting = true;
    new Thread(() -> {
        
        try{
            quickSort(0, nums.size() - 1);
            
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        endTime = System.currentTimeMillis();
        isSorting = false;
        repaint();

    }).start();
    }
    public void startStalinSort() {
    
    currentAlgorithm = "Stalin Sort";
    eliminations = 0;
    startTime = System.currentTimeMillis();
    if(isSorting)return;
    isSorting = true;
    new Thread(() -> {
        try{
            for(int i = 1; i < nums.size();){
                int j = i - 1;
                currentCompareIndex = j;
                
                if(nums.get(i) < nums.get(j)){
                    
                    nums.remove(i);
                    eliminations++;
                }else{
                    i++;
                }
                repaint();
                Thread.sleep(50);
                
            }
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        movingIndex = -1;
        currentCompareIndex = -1;
        endTime = System.currentTimeMillis();
        isSorting = false;
        repaint();
    }).start();
    }
       

       
}
