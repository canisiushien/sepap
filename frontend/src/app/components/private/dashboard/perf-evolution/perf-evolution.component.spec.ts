import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PerfEvolutionComponent } from './perf-evolution.component';

describe('PerfEvolutionComponent', () => {
  let component: PerfEvolutionComponent;
  let fixture: ComponentFixture<PerfEvolutionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PerfEvolutionComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PerfEvolutionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
