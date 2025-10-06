import { TestBed } from '@angular/core/testing';

import { ProficiencyService } from './proficiency.service';

describe('ProficiencyService', () => {
  let service: ProficiencyService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProficiencyService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
