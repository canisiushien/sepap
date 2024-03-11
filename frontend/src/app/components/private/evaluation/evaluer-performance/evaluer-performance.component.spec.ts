import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EvaluerPerformanceComponent } from './evaluer-performance.component';

describe('EvaluerPerformanceComponent', () => {
  let component: EvaluerPerformanceComponent;
  let fixture: ComponentFixture<EvaluerPerformanceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EvaluerPerformanceComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EvaluerPerformanceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
