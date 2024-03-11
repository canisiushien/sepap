import { TestBed } from '@angular/core/testing';

import { ProgrammationService } from './programmation.service';

describe('ProgrammationService', () => {
  let service: ProgrammationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProgrammationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
