package Test_pack;

import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.shim.ChaincodeException;
import org.hyperledger.fabric.shim.ChaincodeStub;
import com.owlike.genson.Genson;


public class Chaincode_test implements ContractInterface {
    private final Genson genson = new Genson();

    // ʵ�����ܺ�Լ�ӿڵ�invoke����
    public void invoke(Context ctx) {
        ChaincodeStub stub = ctx.getStub();
        // ��ȡ���õĺ�������
        String function = stub.getFunction();

        // ���ݺ������ƽ��в�ͬ�Ĵ����߼�
        switch (function) {
            case "storePoints":
                storePoints(ctx, stub.getParameters());
                break;
            case "getPoints":
                getPoints(ctx, stub.getParameters());
                break;
            default:
                // ��֧�ֵĺ������ƣ��׳��쳣
                throw new ChaincodeException("Unsupported function: " + function);
        }
    }

    // �洢���ֵ�������״̬��
    private void storePoints(Context ctx, String[] args) {
        ChaincodeStub stub = ctx.getStub();

        if (args.length != 2) {
            // ��������ȷ���׳��쳣
            throw new ChaincodeException("Incorrect number of arguments. Expecting 2.");
        }

        String userId = args[0];
        int points = Integer.parseInt(args[1]);

        // �����ִ洢��������״̬��
        stub.putStringState(userId, String.valueOf(points));

        System.out.println("Points stored successfully!");
    }

    // ��������״̬�л�ȡ�û�����
    private void getPoints(Context ctx, String[] args) {
        ChaincodeStub stub = ctx.getStub();

        if (args.length != 1) {
            // ��������ȷ���׳��쳣
            throw new ChaincodeException("Incorrect number of arguments. Expecting 1.");
        }

        String userId = args[0];

        // ��������״̬�л�ȡ�û�����
        String points = stub.getStringState(userId);

        System.out.println("Points for user " + userId + ": " + points);
    }

    // �������������������ܺ�Լ
    public static void main(String[] args) {
        // �������ܺ�Լ
        new Chaincode_test().start(args);
    }
}