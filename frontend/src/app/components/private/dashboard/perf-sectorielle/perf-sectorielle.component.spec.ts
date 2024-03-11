import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PerfSectorielleComponent } from './perf-sectorielle.component';

describe('PerfSectorielleComponent', () => {
  let component: PerfSectorielleComponent;
  let fixture: ComponentFixture<PerfSectorielleComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PerfSectorielleComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PerfSectorielleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
