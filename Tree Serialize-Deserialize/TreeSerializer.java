import java.util.*;


//node class with a constructor to initialize a node
class Node{
    Node left;
    Node right;
    int num;

    Node(int num){
        this.num = num;
    }
}

//class to serialize and deserialize a tree
public class TreeSerializer{

    //method to serialize the tree
    public String serialize(Node root){
        StringBuilder sb = new StringBuilder();
        serializeHelper(root, sb);
        return sb.toString();
    }

    //helper method for serializer
    public void serializeHelper(Node root, StringBuilder sb){
        if(root == null){
            sb.append("null").append(",");
            return;
        }

        sb.append(root.num).append(",");
        serializeHelper(root.left, sb);
        serializeHelper(root.right, sb);
    }

    //method to check if the tree has a cycle
    public Boolean checkCycle(Node root){
        Set<Node> visited = new HashSet<>();
        return checkCycleHelper(root, visited);
    }

    //helper method to find a cycle
    public Boolean checkCycleHelper(Node root, Set<Node> visited){
        if(root == null){
            return true;
        }

        if(visited.contains(root)){
            throw new RuntimeException("Cyclic connection is found");
        }

        visited.add(root);
        checkCycleHelper(root.left, visited);
        checkCycleHelper(root.right, visited);

        return false;
    }


    //method to de-serialize the serialized string 
    public Node deSerialize(String str){
        Queue<String> q = new LinkedList<>(Arrays.asList(str.split(",")));
        return deSerializeHelper(q);
    }

    //helper method for de-serializing
    public Node deSerializeHelper(Queue<String> q){
        String s = q.poll();

        if(s.equals("null")){
            return null;
        }

        Node root = new Node(Integer.parseInt(s));
        root.left = deSerializeHelper(q);
        root.right = deSerializeHelper(q);

        return root;
    }
    
    //pre-order traversal of a tree
    public static void preOrder(Node root){
        if(root == null) 
            return;

        System.out.print(root.num+" ");
        preOrder(root.left);
        preOrder(root.right);
    }

    public static void main(String[] args){
        //creating a tree
        Node root = new Node(1); 
        root.left = new Node(2);
        root.right = new Node(1);
        root.left.left = new Node(7);
        root.left.right = new Node(5);
        root.right.right = new Node(28);
        root.left.left.left = new Node(4);

        System.out.println("\nTree before Serialization: "); 
        TreeSerializer.preOrder(root);

        TreeSerializer serialize = new TreeSerializer();

        //checking for cyclic tree and serializing
        try{
            Boolean isCyclic = serialize.checkCycle(root);
            if(!isCyclic){
                System.out.println("\n\nNo cycle detected!");
            }
        }catch(RuntimeException e){
            System.out.println("\n\nError: "+e);
        }
        
        //calling serializer function
        String serializedString = serialize.serialize(root);
        System.out.println("\nSerialized string: "+serializedString);

        //calling deserializer function
        TreeSerializer deSerialize = new TreeSerializer();
        Node newRoot = deSerialize.deSerialize(serializedString);

        //checking the output of desrialized tree
        System.out.println("\nTree after Deserialization: ");
        TreeSerializer.preOrder(newRoot);

    }
}