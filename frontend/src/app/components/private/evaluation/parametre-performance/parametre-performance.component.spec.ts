import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ParametrePerformanceComponent } from './parametre-performance.component';

describe('ParametrePerformanceComponent', () => {
  let component: ParametrePerformanceComponent;
  let fixture: ComponentFixture<ParametrePerformanceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ParametrePerformanceComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ParametrePerformanceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
