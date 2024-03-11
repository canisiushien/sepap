import { TestBed } from '@angular/core/testing';

import { GrillePerformanceService } from './grille-performance.service';

describe('GrillePerformanceService', () => {
  let service: GrillePerformanceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GrillePerformanceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
