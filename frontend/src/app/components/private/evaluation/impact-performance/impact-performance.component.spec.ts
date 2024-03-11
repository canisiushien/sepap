import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ImpactPerformanceComponent } from './impact-performance.component';

describe('ImpactPerformanceComponent', () => {
  let component: ImpactPerformanceComponent;
  let fixture: ComponentFixture<ImpactPerformanceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ImpactPerformanceComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ImpactPerformanceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
