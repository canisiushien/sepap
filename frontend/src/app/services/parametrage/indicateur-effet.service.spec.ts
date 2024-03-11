import { TestBed } from '@angular/core/testing';

import { IndicateurEffetService } from './indicateur-effet.service';

describe('IndicateurEffetService', () => {
  let service: IndicateurEffetService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(IndicateurEffetService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
