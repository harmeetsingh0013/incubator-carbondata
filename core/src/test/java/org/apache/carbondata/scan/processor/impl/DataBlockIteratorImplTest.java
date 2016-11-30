/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.carbondata.scan.processor.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.carbondata.core.carbon.datastore.IndexKey;
import org.apache.carbondata.core.carbon.metadata.datatype.DataType;
import org.apache.carbondata.core.carbon.querystatistics.QueryStatistic;
import org.apache.carbondata.core.carbon.querystatistics.QueryStatisticsModel;
import org.apache.carbondata.core.datastorage.store.FileHolder;
import org.apache.carbondata.core.datastorage.store.impl.FileHolderImpl;
import org.apache.carbondata.scan.executor.infos.AggregatorInfo;
import org.apache.carbondata.scan.executor.infos.BlockExecutionInfo;
import org.apache.carbondata.scan.model.QueryDimension;
import org.apache.carbondata.scan.model.SortOrderType;
import org.apache.carbondata.scan.result.impl.FilterQueryScannedResult;

import org.junit.Before;
import org.junit.Test;

public class DataBlockIteratorImplTest {

  private BlockExecutionInfo blockExecutionInfo;
  private DataBlockIteratorImpl dataBlockIterator;
  private QueryStatisticsModel queryStatisticsModel;
  private FileHolder fileHolder;

  @Before
  public void init() {
    //FilterQueryScannedResult scannedResult = new FilterQueryScannedResult();
    blockExecutionInfo = new BlockExecutionInfo();
    blockExecutionInfo.setLimit(-1);
    blockExecutionInfo.setStartKey(new IndexKey(new byte[]{1, 0, 0}, new byte[]{}));
    blockExecutionInfo.setEndKey(new IndexKey(new byte[]{2, 11, 11}, new byte[]{}));
    blockExecutionInfo.setMaskedByteForBlock(new int[]{0, -1, -1});
    blockExecutionInfo.setTotalNumberDimensionBlock(3);
    blockExecutionInfo.setTotalNumberOfMeasureBlock(1);

    QueryDimension queryDimension = new QueryDimension("country");
    queryDimension.setSortOrder(SortOrderType.NONE);
    queryDimension.setQueryOrder(0);
    queryDimension.setAggregateFunction("dummy");
    blockExecutionInfo.setQueryDimensions(new QueryDimension[]{queryDimension});

    AggregatorInfo aggregatorInfo = new AggregatorInfo();
    aggregatorInfo.setMeasureOrdinals(new int[0]);
    aggregatorInfo.setMeasureExists(new boolean[0]);
    aggregatorInfo.setDefaultValues(new Object[0]);
    aggregatorInfo.setMeasureDataTypes(new DataType[0]);
    blockExecutionInfo.setAggregatorInfo(aggregatorInfo);

    fileHolder = new FileHolderImpl();

    queryStatisticsModel = new QueryStatisticsModel();
    Map<String, QueryStatistic> statisticMap = new HashMap<>();
    statisticMap.put("The num of valid scanned blocklet", new QueryStatistic());
    statisticMap.put("The num of total blocklet", new QueryStatistic());

    queryStatisticsModel.setStatisticsTypeAndObjMap(statisticMap);
    dataBlockIterator = new DataBlockIteratorImpl(blockExecutionInfo, fileHolder, 10000, queryStatisticsModel);
  }

  @Test
  public void testNext() {
    List<Object[]> result = dataBlockIterator.next();
  }
}
