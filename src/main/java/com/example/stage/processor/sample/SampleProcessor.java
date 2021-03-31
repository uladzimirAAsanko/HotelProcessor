/**
 * Copyright 2015 StreamSets Inc.
 *
 * Licensed under the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.stage.processor.sample;

import com.byteowls.jopencage.JOpenCageGeocoder;
import com.byteowls.jopencage.model.JOpenCageForwardRequest;
import com.byteowls.jopencage.model.JOpenCageLatLng;
import com.byteowls.jopencage.model.JOpenCageResponse;
import com.example.stage.entity.GeoHashGenerator;
import com.example.stage.entity.HotelData;
import com.example.stage.lib.sample.Errors;

import com.example.stage.parser.HotelParser;
import com.streamsets.pipeline.api.Field;
import com.streamsets.pipeline.api.Record;
import com.streamsets.pipeline.api.StageException;
import com.streamsets.pipeline.api.base.SingleLaneRecordProcessor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public abstract class SampleProcessor extends SingleLaneRecordProcessor {
  private static final Logger LOG = LoggerFactory.getLogger(SampleProcessor.class);
  public abstract String getConfig();

  @Override
  protected List<ConfigIssue> init() {
    List<ConfigIssue> issues = super.init();

    if (getConfig().equals("invalidValue")) {
      issues.add(
          getContext().createConfigIssue(
              Groups.SAMPLE.name(), "config", Errors.SAMPLE_00, "Here's what's wrong..."
          )
      );
    }
    return issues;
  }

  /** {@inheritDoc} */
  @Override
  public void destroy() {
    super.destroy();
  }

  /** {@inheritDoc} */
  @Override
  protected void process(Record record, SingleLaneBatchMaker batchMaker) throws StageException {
    LOG.info("Input record: {}", record);
    LOG.info("Inside record: {}", record.get("/"));
    LOG.info("Inside record: {}", record.get("/"));
    LOG.info("GetPaths: {}",  record.getEscapedFieldPaths());
    if(record.get("/5") == null || record.get("/6") == null){
      return;
    }
    if(HotelParser.getValue(record,5).equals(HotelParser.NULL_DATA) ||
            HotelParser.getValue(record,6).equals(HotelParser.NULL_DATA)){
      JOpenCageLatLng firstResultLatLng = mapLngLat(record);
      record.set("/5", Field.create(firstResultLatLng.getLng()));
      record.set("/6", Field.create(firstResultLatLng.getLat()));
    }
    HotelData hotel = HotelParser.parse(record);
    String hash = GeoHashGenerator.generateGeoHash(hotel);
    record.set("/7", Field.create(hash));
    batchMaker.addRecord(record);
    LOG.info("Generated hash is: {}", hash);
    LOG.info("Record is over");
  }


  private JOpenCageLatLng mapLngLat(Record record){
    JOpenCageGeocoder jOpenCageGeocoder = new JOpenCageGeocoder("b9e25eae43e5457b94284d92da69d15e");
    JOpenCageForwardRequest request = new JOpenCageForwardRequest(HotelParser.getValue(record, 4));
    request.setRestrictToCountryCode(HotelParser.getValue(record, 2));
    JOpenCageResponse response = jOpenCageGeocoder.forward(request);
    return response.getFirstPosition();
  }
}