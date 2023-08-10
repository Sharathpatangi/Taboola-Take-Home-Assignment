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

//class to serialize and deserialize a cyclic tree
public class CyclicTreeSerializer {
    //method to serialize the tree
    public String serialize(Node root){

        Set<Node> visited = new HashSet<>();
        StringBuilder sb = new StringBuilder();
        serializeHelper(root, sb, visited);
        return sb.toString();
    }

    //helper method for serializer
    public void serializeHelper(Node root, StringBuilder sb, Set<Node> visited){
        if(root == null){
            sb.append("null").append(",");
            return;
        }

        if (visited.contains(root)) {
            sb.append("$"+root.num).append(",");
            return;
        }

        sb.append(root.num).append(",");
        visited.add(root);
        serializeHelper(root.left, sb, visited);
        serializeHelper(root.right, sb, visited);
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

        if (s.startsWith("$")) {
            // Handle cyclic reference
            return new Node(Integer.parseInt(s.substring(1)));
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
        root.right = new Node(3);
        root.left.left = new Node(4);
        root.left.right = new Node(5);
        root.right.right = root.left.left;

        CyclicTreeSerializer serialize = new CyclicTreeSerializer();
        //calling serializer function
        String serializedString = serialize.serialize(root);
        System.out.println("\nSerialized string: "+serializedString);

        //calling deserializer function
        CyclicTreeSerializer deserialize = new CyclicTreeSerializer();

        Node newRoot = deserialize.deSerialize(serializedString);

        //checking the output of desrialized tree
        System.out.println("\nTree after Deserialization: ");
        TreeSerializer.preOrder(newRoot);
    }
}
