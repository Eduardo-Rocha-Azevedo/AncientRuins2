package ai;

import java.util.ArrayList;

import principal.GamePanel;

public class PathFinder{

    GamePanel gp;
    Node[][] node;
    ArrayList<Node> openList = new ArrayList<>();
    public ArrayList<Node> pathList = new ArrayList<>();
    Node startNode, goalNode, currentNode;
    boolean goalReached = false;
    int step = 0;

    public PathFinder(GamePanel gp) {
        this.gp = gp;  
        instantiateNodes();
    }

    public void instantiateNodes(){
        node = new Node[gp.maxWorldCol][gp.maxWorldRow];

        int col = 0;
        int row = 0;
        
        while(col < gp.maxWorldCol && row < gp.maxWorldRow){
            node[col][row] = new Node(col, row);
            col++;
            if(col == gp.maxWorldCol){
                col = 0;
                row++;
            }
        }
    }
    public void resetNodes(){
        int col = 0;
        int row = 0;

        while(col < gp.maxWorldCol && row < gp.maxWorldRow){
            node[col][row].open = false;
            node[col][row].checked = false;
            node[col][row].solid = false;
            col++;

            if(col == gp.maxWorldCol){
                col = 0;
                row++;
            }
        }
        // RESET OTHERS SETTINGS
        openList.clear();
        pathList.clear();
        goalReached = false;
        step = 0;
    }
    public void setNodes(int startCol, int startRow, int goalCol, int goalRow){
        resetNodes();

        // SET START NODE
        startNode = node[startCol][startRow];
        currentNode = startNode;
        goalNode = node[goalCol][goalRow];
        openList.add(currentNode);

        int col = 0;
        int row = 0;

        while(col < gp.maxWorldCol && row < gp.maxWorldRow){
            // set solid Nodes
            // check tiles
            int tileNum = gp.tileM.mapTileNum[gp.currentMap][col][row];
            if(gp.tileM.tile[tileNum].collision == true){
                node[col][row].solid = true;
            }
            // check interactive tiles
            for(int i = 0; i < gp.iTile[1].length; i++){
                if(gp.iTile[gp.currentMap][i] != null && gp.iTile[gp.currentMap][i].destructible == true){
                    int itCol = gp.iTile[gp.currentMap][i].worldX / gp.tileSize;
                    int itRow = gp.iTile[gp.currentMap][i].worldY / gp.tileSize;
                    node[itCol][itRow].solid = true;
                }
            }
            //Set cost
            getCost(node[col][row]);
            col++;
            if(col == gp.maxWorldCol){
                col = 0;
                row++;
            }
        }
    }

    public void getCost(Node node){
        // G COST
        int xDisance = Math.abs(node.col - startNode.col);
        int yDisance = Math.abs(node.row - startNode.row);
        node.gCost = xDisance + yDisance;

        // H COST
        xDisance = Math.abs(node.col - goalNode.col);
        yDisance = Math.abs(node.row - goalNode.row);
        node.hCost = xDisance + yDisance;

        // F COST
        node.fCost = node.gCost + node.hCost;
    }

    public boolean search(){
        while(goalReached == false && step < 500){
            int col = currentNode.col;
            int row = currentNode.row;

            // Check the current node
            currentNode.checked = true;
            openList.remove(currentNode);

            // Open the Up node
            if(row - 1 >= 0 ){ openNode(node[col][row - 1]);}  
            // Open the Down node
            if(row + 1 < gp.maxWorldRow){ openNode(node[col][row + 1]);}
            // Open the Left node
            if(col - 1 >= 0){ openNode(node[col - 1][row]);}
            // Open the Right node
            if(col + 1 < gp.maxWorldCol){ openNode(node[col + 1][row]);}
            
            // Find the best node
            int bestNodeIndex = 0;
            int bestNodeFCost = 999;

            for(int i = 0; i < openList.size(); i++){
                if(openList.get(i).fCost < bestNodeFCost){
                    bestNodeFCost = openList.get(i).fCost;
                    bestNodeIndex = i;
                }
                // if F cost is equal, check g cost
                else if(openList.get(i).fCost == bestNodeFCost){
                    if(openList.get(i).gCost < openList.get(bestNodeIndex).gCost){
                        bestNodeIndex = i;
                    }
                }
            }
            // if there isn't node in the openlist, end the loop
            if(openList.size() == 0){
                break;
            }
            //After the loop, openList[bestNodeIndex] is the next step (=currentNode)
            currentNode = openList.get(bestNodeIndex);
            
            if(currentNode == goalNode){
                goalReached = true;
                trackThePath();
            } 
            step++;
        }
        return goalReached;
    }
    public void openNode(Node node){
        if(node.open == false && node.checked == false && node.solid == false){
            node.open = true;
            node.parent = currentNode;
            openList.add(node);
        }
    }
    public void trackThePath(){
        Node current = goalNode;
        while(current != startNode){
            pathList.add(0,current);
            current = current.parent;
        }
    }
}
 