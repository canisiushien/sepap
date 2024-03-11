import { TestBed } from '@angular/core/testing';

import { EvaluationGouvernanceService } from './evaluation-gouvernance.service';

describe('EvaluationGouvernanceService', () => {
  let service: EvaluationGouvernanceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EvaluationGouvernanceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
