import { TestBed } from '@angular/core/testing';

import { ParametrerImpactService } from './parametrer-impact.service';

describe('ParametrerImpactService', () => {
  let service: ParametrerImpactService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ParametrerImpactService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
