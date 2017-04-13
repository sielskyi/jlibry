/**
 * 	DeviceUnit class.
 *
 *   @author    sielskyi (SLL)
 *   @version   01.01 2016/12/21 Initial version.
 */

package com.sielskyi.integralconfig;

import java.util.ArrayList;
import java.util.List;
import com.sielskyi.integralconfig.InputUnit;
import com.sielskyi.integralconfig.OutputUnit;
import com.sielskyi.integralconfig.IfaceUnit;
import com.sielskyi.integralconfig.GroupUnit;
import com.sielskyi.integralconfig.UserUnit;

public class DeviceUnit extends Unit{

    /* Public constants */
    public static final int DSC_DEVICE_GUARD = 1;

    public static final int PRODUCER_UNKNOWN = 0;
    public static final int PRODUCER_INTEGRAL = 1;

    public static final int MODEL_UNKNOWN = 0;
    public static final int MODEL_INTEGRAL_UOO = 17;
    public static final int MODEL_INTEGRAL_IFC = 18;

    /* Private constants */
    private static final int PRIVATE = 0;

    /* Variables */
    private int     model;
    private String  modelString;
    private int     producer;
    private String  producerString;
    public UnitList<InputUnit> inputs;
    public UnitList<OutputUnit>    outputs;
    public UnitList<IfaceUnit> ifaces;
    public UnitList<GroupUnit> groups;
    public UnitList<UserUnit>  users;

    public DeviceUnit() {
        clear();
    }

    public DeviceUnit(int type, int dsc) {
        clear();
        super.setType(type);
        super.setDsc(dsc);
    }

    /** Clear code to none value. */
    public void clear() {
        super.clear();
        super.setType(TYPE_DEVICE);
        model = 0;
        modelString = "";
        producer = 0;
        producerString = "";
        inputs = new UnitList<InputUnit>();
        outputs = new UnitList<OutputUnit>();
        ifaces = new UnitList<IfaceUnit>();
        groups = new UnitList<GroupUnit>();
        users = new UnitList<UserUnit>();

    }

    public int getProducer() {
        return producer;
    }

    public void setProducer(int producer) {
        this.producer = producer;
    }

    public String getProducerString() {
        return producerString;
    }

    public int getModel() {
        return model;
    }

    public void setModel(int model) {
        this.model = model;
    }

    public String getModelString() {
        return modelString;
    }

    /** Get string of unit type. */
    public String getTypeString() {
        return "device";
    }

    /** Get string of unit descriptor. */
    public String getDscString() {
        String str;

        str = "device ";
        switch (this.getDsc()) {
            case DSC_DEVICE_GUARD:
                str += "guard";
                break;
            default:
                str += "unknown";
                break;
        }
        return str;
    }

    /*
    public boolean createInput(int num)
    {
        Input input = new Input();

        return setInput(num, input);
    }

    public boolean setInput(int num, Input input)
    {

        return false;
    }

    public Input getInput(int num)
    {

        return null;
    }

    public boolean deleteInput(int num)
    {
        return false;
    }

    public boolean isInputExists(int num)
    {

    }

    public boolean createOutput(int num)
    {
        Output output = new Output();

        return setOutput(num, output);
    }

    public boolean setOutput(int num, Output output)
    {

        return false;
    }

    public Output getOutput(int num)
    {

        return null;
    }

    public boolean deleteOutput(int num)
    {
        return false;
    }

    public boolean setIface(int num, Iface iface)
    {

        return false;
    }

    public Iface getIface(int num)
    {

        return null;
    }

    public boolean deleteIface(int num)
    {
        return false;
    }

    public boolean setGroup(int num, Group group)
    {

        return false;
    }

    public Group getGroup(int num)
    {

        return null;
    }

    public boolean deleteGroup(int num)
    {
        return false;
    }

    public boolean setUser(int num, User user)
    {

        return false;
    }

    public User getUser(int num)
    {

        return null;
    }

    public boolean deleteUser(int num)
    {
        return false;
    }

    */
}
