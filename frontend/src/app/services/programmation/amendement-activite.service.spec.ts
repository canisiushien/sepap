import { TestBed } from '@angular/core/testing';

import { AmendementActiviteService } from './amendement-activite.service';

describe('AmendementActiviteService', () => {
  let service: AmendementActiviteService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AmendementActiviteService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
