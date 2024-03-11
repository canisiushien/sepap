import { TestBed } from '@angular/core/testing';

import { MinisterStructureService } from './minister-structure.service';

describe('MinisterStructureService', () => {
  let service: MinisterStructureService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MinisterStructureService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
