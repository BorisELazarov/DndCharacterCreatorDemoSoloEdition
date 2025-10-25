import { TestBed } from '@angular/core/testing';

import { FeatureServiceService } from './feature.service';

describe('FeatureServiceService', () => {
  let service: FeatureServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FeatureServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
