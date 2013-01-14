/*
 * Copyright to the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.rioproject.test.system.measurable.cpu;

import net.jini.config.EmptyConfiguration;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.rioproject.system.MeasuredResource;
import org.rioproject.system.measurable.SigarHelper;
import org.rioproject.test.system.measurable.SimpleThresholdListener;
import org.rioproject.system.measurable.cpu.CPU;
import org.rioproject.system.measurable.cpu.CpuUtilization;
import org.rioproject.watch.ThresholdValues;

/**
 * Simple test of DiskSpace class using SIGAR
 */
public class CPUTestUsingSigar {
    @Before
    public void checkSigar() {
        Assert.assertTrue(SigarHelper.sigarAvailable());
    }
    @Test
    public void createAndVerifyCPUClassWithSigar() {
        CPU cpu = new CPU(EmptyConfiguration.INSTANCE);
        Assert.assertTrue(SigarHelper.sigarAvailable());
        cpu.start();
        cpu.checkValue();
        MeasuredResource mRes = cpu.getMeasuredResource();
        Assert.assertTrue("MeasuredResource should not be null", mRes!=null);
        Assert.assertTrue("MeasuredResource should be a CPUUtilization", mRes instanceof CpuUtilization);
        double utilization = cpu.getUtilization();
        Assert.assertTrue("Utilization should be > 0", utilization>0);
    }

    @Test
    public void createAndVerifyCPUWithLowerThresholdBeingCrossedWithSigar() {
        CPU cpu = new CPU(EmptyConfiguration.INSTANCE);
        ThresholdValues tVals = new ThresholdValues(0.30, 0.90);
        cpu.setThresholdValues(tVals);
        SimpleThresholdListener l = new SimpleThresholdListener();
        cpu.start();
        cpu.checkValue();
        double utilization = cpu.getUtilization();
        Assert.assertTrue("Utilization should be > 0", utilization>0);
        Assert.assertTrue(l.getType()== null);
    }

    @Test
    public void createAndVerifyCPUWithUpperThresholdBeingCrossedWithSigar() {
        CPU cpu = new CPU(EmptyConfiguration.INSTANCE);
        ThresholdValues tVals = new ThresholdValues(0.00, 0.05);
        cpu.setThresholdValues(tVals);
        SimpleThresholdListener l = new SimpleThresholdListener();
        cpu.start();
        cpu.checkValue();
        double utilization = cpu.getUtilization();
        Assert.assertTrue("Utilization should be > 0", utilization>0);
        Assert.assertTrue(l.getType()==null);
    }
}